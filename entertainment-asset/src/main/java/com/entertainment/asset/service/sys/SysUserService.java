package com.entertainment.asset.service.sys;

import com.entertainment.asset.bean.sys.RegisterBean;
import com.entertainment.asset.config.RedisOperation;
import com.entertainment.asset.config.YunpianSmsSender;
import com.entertainment.asset.constant.RedisConstant;
import com.entertainment.asset.dao.sys.SysRegisterEmailRepository;
import com.entertainment.asset.dao.sys.SysUserRepository;
import com.entertainment.asset.dao.sys.TbUserRepository;
import com.entertainment.asset.entity.sys.SysRegisterEmail;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.entity.sys.TbUser;
import com.entertainment.asset.utils.StringUtil;
import com.entertainment.common.exception.BusinessException;
import com.entertainment.common.exception.STException;
import com.entertainment.common.page.PageableRequest;
import com.entertainment.common.page.PageableResponse;
import com.entertainment.common.type.EmailSendStatus;
import com.entertainment.common.type.sys.UserStatus;
import com.entertainment.common.type.sys.UserTypeEnum;
import com.entertainment.common.utils.PageableConverter;
import com.entertainment.common.utils.Preconditions;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysUserService
 *  @package: com.entertainment.asset.service.sys
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRegisterEmailRepository sysRegisterEmailRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private YunpianSmsSender yunpianSmsSender;

    @Autowired
    private TbUserRepository tbUserRepository;

    @Value("${shiro.credentialsSalt}")
    private String credentialsSalt;

    /**
     * 短信发送等待时间(单位：秒)
     */
    private static final Long SEND_TIME = 600L;

    @Value("${sms.text.registerSaveInfo}")
    private String content;

    public SysUser findSysUserByEmail(String email){
        return sysUserRepository.findByEmailAndDeletedIsFalse(email);
    }

    public PageableResponse<SysUser> findByPage(PageableRequest pageable){
        PageableResponse<SysUser> pageableResponse = new PageableResponse<SysUser>();
        Page<SysUser> page = sysUserRepository.findAllByDeletedIsFalse(PageableConverter.toPageRequest(pageable));
        pageableResponse.setList(page.getContent());
        pageableResponse.setTotalCount(page.getSize());
        pageableResponse.setTotalPages(page.getTotalPages());
        return pageableResponse;
    }


    /**
     * 发送短信验证码
     * @param phone
     * @param zone
     */
    public void send(String phone,String zone) throws STException{
        if(Preconditions.isBlank(phone)){
            throw new STException("手机号码为空");
        }
        if(Preconditions.isBlank(zone)){
            throw new STException("时区为空");
        }
        String mobile = "+" + zone + phone;
        checkVerifyCode(mobile);
        String verifyCode = StringUtil.generateRandomNumStr(6);
        redisOperation.saveAuthorizationVerifyCode(mobile, verifyCode);
        String key = String.format("%s%s", RedisConstant.PREFIX_REGISTER_VERIFY_CODE_SEND_TIME_KEY, mobile);
        redisTemplate.opsForValue().set(key, "TIME", SEND_TIME, TimeUnit.SECONDS);
        yunpianSmsSender.registerSmsForContent(mobile, String.format(content, verifyCode));
    }

    /**
     * 校验短信验证码是否发送频繁
     *
     * @param mobile
     */
    public void checkVerifyCode(String mobile) {
        String code = redisOperation.getAuthorizationVerifyCode(mobile);
        if (Preconditions.isNotBlank(code)) {
            String key = String.format("%s%s", RedisConstant.PREFIX_REGISTER_VERIFY_CODE_SEND_TIME_KEY, mobile);
            Long time = redisTemplate.getExpire(key);
            if (Preconditions.isNotBlank(time) && time > 0) {
                throw new STException(String.format("验证码发送过于频繁，请%s秒后重试", time));
            }
        }
    }

    public void register(RegisterBean registerBean) throws STException{
        String mobile = "+" + registerBean.getZoneCode() + registerBean.getPhone();
        String code = redisOperation.getAuthorizationVerifyCode(mobile);
        if(Preconditions.isBlank(registerBean.getSmsCode())){
            throw new STException("验证码为空");
        }
        if(Preconditions.isBlank(code)){
            throw new STException("验证码已过期");
        }
        if(!code.equals(registerBean.getSmsCode())){
            //TODO 验证码输入错误次数过多处理
            throw new STException("验证码输入错误");
        }

        TbUser tbUser = findByPhone(registerBean.getPhone());
        if(Preconditions.isNotBlank(tbUser)){
            throw new STException("手机号码已被注册");
        }
        tbUser = new TbUser();
        TbUser adminUser = null;
        if(Preconditions.isNotBlank(registerBean.getAgentArea())){
            //查询管理员
            adminUser = tbUserRepository.findByDeletedIsFalseAndUserTypeAndAgentArea(UserTypeEnum.ADMIN_USER, registerBean.getAgentArea());
            if(Preconditions.isNotBlank(adminUser)){
                tbUser.setParentId(adminUser.getUserId());
            }
        }
//        String password = (new SimpleHash("MD5", registerBean.getPassword(), ByteSource.Util.bytes(credentialsSalt), 10)).toString();
        tbUser.setPhoneNum(registerBean.getPhone());
        tbUser.setUserName(registerBean.getPhone());
        tbUser.setPassWord(registerBean.getPassword());
        tbUser.setRoleId(2L);
        //0 普通用户 1管理员 2公司老总
        tbUser.setUserType(UserTypeEnum.GENERAL_USER);
        tbUser.setStatus(UserStatus.NORMAL);
        tbUser.setSex(0);
        tbUser.setAgentArea(registerBean.getAgentArea());
        tbUserRepository.save(tbUser);
    }

    public TbUser findByPhone(String phone){
        return tbUserRepository.findByDeletedIsFalseAndPhoneNum(phone);
    }

//    @Transactional(rollbackFor = Exception.class)
//    public ResponseContent register(SysUser sysUser){
//        SysUser emailUser = sysUserRepository.findByEmailAndDeletedIsFalse(sysUser.getEmail());
//        if(Preconditions.isNotBlank(emailUser)){
//            return ResponseContent.buildFail("该邮箱已注册！");
//        }
//        SysUser saveUser = new SysUser();
//        BeanUtils.copyProperties(sysUser,saveUser);
//        saveUser.setStatus(EmailSendStatus.EMAIL_VALIDATION_ING);
//        SysUser newUser = sysUserRepository.save(saveUser);
//        if(Preconditions.isBlank(newUser)){
//            //TODO 注意多语言
//            throw new BusinessException("保存用户异常");
//        }
//
//        EmailTemplate emailTemplate = emailTemplateService.getTemplateByType(EmailType.EMAIL_REGISTERED);
//
//        String verifyCode = UUID.randomUUID().toString();
//        String content = emailTemplate.getContent().replaceAll("\\{verify_code}",verifyCode);
//        mailService.sendHtmlMail(newUser.getEmail(),TITLE,content);
////        if(response.getCode() == ResponseContent.FAIL_CODE){
////            Future<ResponseContent> future = threadSimple.submit(() -> mailService.sendHtmlMail(newUser.getEmail(),TITLE,content));
////        }
//        //发邮件保存信息到数据库
//        SysRegisterEmail sysRegisterEmail = new SysRegisterEmail();
//        sysRegisterEmail.setContent(content);
//        sysRegisterEmail.setStatus(EmailSendStatus.EMAIL_VALIDATION_ING);
//        sysRegisterEmail.setSysUserId(newUser.getId());
//        sysRegisterEmail.setTitle(TITLE);
//        sysRegisterEmail.setVerifyCode(verifyCode);
//        sysRegisterEmailRepository.save(sysRegisterEmail);
//        return ResponseContent.buildSuccess();
//    }

    public ResponseContent activation(String verifyCode){
        SysRegisterEmail sysRegisterEmail = sysRegisterEmailRepository.findByVerifyCodeAndDeletedIsFalse(verifyCode);
        sysRegisterEmail.setStatus(EmailSendStatus.EMAIL_VALIDATION_SUCCEE);
        sysRegisterEmailRepository.save(sysRegisterEmail);
        return ResponseContent.buildSuccess();
    }

}
