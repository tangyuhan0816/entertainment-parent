package com.vpis.asset.service.sys;

import com.vpis.asset.bean.sys.BackPassBean;
import com.vpis.asset.bean.sys.RegisterBean;
import com.vpis.asset.constant.RedisConstant;
import com.vpis.asset.repository.sys.TbUserRepository;
import com.vpis.common.entity.sys.TbUser;
import com.vpis.common.exception.BusinessException;
import com.vpis.common.exception.STException;
import com.vpis.common.type.sys.RoleTypeEnum;
import com.vpis.common.type.sys.UserStatus;
import com.vpis.common.type.sys.UserTypeEnum;
import com.vpis.common.utils.Preconditions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private TbUserRepository tbUserRepository;

    @Autowired
    private SendSmsService sendSmsService;

    @Value("${shiro.credentialsSalt}")
    private String credentialsSalt;

    @Value("${sms.text.registerSaveInfo}")
    private String registerContent;

    @Value("${sms.text.loginSaveInfo}")
    private String loginContent;

    @Value("${sms.text.backPassInfo}")
    private String backPassContent;


    public TbUser findByPhone(String phone){
        return tbUserRepository.findByPhoneNumAndDeletedIsFalse(phone);
    }

    public TbUser findById(Long userId){
        return tbUserRepository.findByUserIdAndDeletedIsFalse(userId);
    }

    /**
     * 发送注册短信验证码
     * @param phone
     */
    public void send(String phone) throws STException{
        sendSmsService.send(phone,registerContent,RedisConstant.PREFIX_REGISTER_VERIFY_CODE_SEND_TIME_KEY,RedisConstant.PREFIX_REGISTER_VERIFY_CODE_KEY);
    }

    /**
     * 发送登陆短信验证码
     * @param phone
     */
    public void sendLogin(String phone) throws STException{
        sendSmsService.send(phone,loginContent,RedisConstant.PREFIX_LOGIN_VERIFY_CODE_SEND_TIME_KEY,RedisConstant.PREFIX_LOGIN_VERIFY_CODE_KEY);
    }

    /**
     * 发送登陆短信验证码
     * @param phone
     */
    public void sendBack(String phone) throws STException{
        sendSmsService.send(phone,backPassContent,RedisConstant.PREFIX_BACK_PASS_VERIFY_CODE_SEND_TIME_KEY,RedisConstant.PREFIX_BACK_PASS_VERIFY_CODE_KEY);
    }



    public void register(RegisterBean registerBean) throws STException{

        //校验验证码
        sendSmsService.checkVerifyCode(registerBean.getSmsCode(),registerBean.getPhone(), RedisConstant.PREFIX_REGISTER_VERIFY_CODE_KEY);

        TbUser tbUser = findByPhone(registerBean.getPhone());

        if(Preconditions.isNotBlank(tbUser)){

            throw new STException("手机号码已被注册");
        }

        tbUser = new TbUser();

        if(Preconditions.isBlank(registerBean.getAgentArea())){

            throw new STException("区域编码为空");
        }

        //查询管理员
        TbUser adminUser = tbUserRepository.findByAgentAreaAndUserTypeAndDeletedIsFalse(registerBean.getAgentArea(), UserTypeEnum.ADMIN_USER);

        if(Preconditions.isBlank(adminUser)){

            throw new STException("当前区域没有代理商");
        }

        tbUser.setParentId(adminUser.getUserId());

        String password = (new SimpleHash("MD5", registerBean.getPassword(), ByteSource.Util.bytes(credentialsSalt), 10)).toString();

        tbUser.setPhoneNum(registerBean.getPhone());

        tbUser.setUserName(registerBean.getPhone());

        tbUser.setPassWord(password);

        tbUser.setRoleType(RoleTypeEnum.SHITOU);

        tbUser.setUserType(UserTypeEnum.GENERAL_USER);

        tbUser.setStatus(UserStatus.NORMAL);

        tbUser.setAgentArea(registerBean.getAgentArea());

        tbUserRepository.save(tbUser);
    }

    public void backPass(BackPassBean bean){
        if(!bean.getNewPassWord().equals(bean.getSurePassWord())){

            throw new BusinessException("两次密码输入不一致");
        }

        TbUser tbUser = null;

        if(Preconditions.isNotBlank(bean.getSmsCode())){

            sendSmsService.checkVerifyCode(bean.getSmsCode(),bean.getPhone(),RedisConstant.PREFIX_BACK_PASS_VERIFY_CODE_KEY);

            tbUser = findByPhone(bean.getPhone());

            String password = (new SimpleHash("MD5", bean.getSurePassWord(), ByteSource.Util.bytes(credentialsSalt), 10)).toString();

            tbUser.setPassWord(password);

        } else {
            tbUser = findByPhone(bean.getPhone());

            if(tbUser.getPassWord().equals(bean.getOldPassWord())){

                throw new BusinessException("密码输入错误");
            }

            String password = (new SimpleHash("MD5", bean.getSurePassWord(), ByteSource.Util.bytes(credentialsSalt), 10)).toString();

            tbUser.setPassWord(password);
        }
        tbUserRepository.save(tbUser);

    }

    public void saveUser(TbUser user){
        tbUserRepository.save(user);
    }

    public static void main(String[] args) {
        System.out.println((new SimpleHash("MD5", "abcd1234", ByteSource.Util.bytes("c925b2643f15b14e6cb474027f8c13b8"), 10)).toString());
    }

}
