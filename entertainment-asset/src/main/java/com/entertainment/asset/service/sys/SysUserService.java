package com.entertainment.asset.service.sys;

import com.entertainment.asset.dao.sys.SysRegisterEmailRepository;
import com.entertainment.asset.dao.sys.SysUserRepository;
import com.entertainment.asset.entity.sys.EmailTemplate;
import com.entertainment.asset.entity.sys.SysRegisterEmail;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.base.BaseCacheService;
import com.entertainment.asset.service.email.EmailTemplateService;
import com.entertainment.asset.service.email.MailService;
import com.entertainment.common.exception.BusinessException;
import com.entertainment.common.page.PageableRequest;
import com.entertainment.common.page.PageableResponse;
import com.entertainment.common.type.EmailSendStatus;
import com.entertainment.common.type.EmailType;
import com.entertainment.common.utils.PageableConverter;
import com.entertainment.common.utils.Preconditions;
import com.entertainment.common.utils.ResponseContent;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysUserService
 *  @package: com.entertainment.asset.service.sys
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class SysUserService extends BaseCacheService<SysUser>{

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SysRegisterEmailRepository sysRegisterEmailRepository;

    private static final String TITLE = "注册邮件";

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("simple-pool-%s").build();

    private static final ExecutorService threadSimple = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),threadFactory);

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

    @Transactional(rollbackFor = Exception.class)
    public ResponseContent register(SysUser sysUser){
        SysUser emailUser = sysUserRepository.findByEmailAndDeletedIsFalse(sysUser.getEmail());
        if(Preconditions.isNotBlank(emailUser)){
            return ResponseContent.buildFail("该邮箱已注册！");
        }
        SysUser saveUser = new SysUser();
        BeanUtils.copyProperties(sysUser,saveUser);
        saveUser.setStatus(EmailSendStatus.EMAIL_VALIDATION_ING);
        SysUser newUser = sysUserRepository.save(saveUser);
        if(Preconditions.isBlank(newUser)){
            //TODO 注意多语言
            throw new BusinessException("保存用户异常");
        }

        EmailTemplate emailTemplate = emailTemplateService.getTemplateByType(EmailType.EMAIL_REGISTERED);

        String verifyCode = UUID.randomUUID().toString();
        String content = emailTemplate.getContent().replaceAll("\\{verify_code}",verifyCode);
        mailService.sendHtmlMail(newUser.getEmail(),TITLE,content);
//        if(response.getCode() == ResponseContent.FAIL_CODE){
//            Future<ResponseContent> future = threadSimple.submit(() -> mailService.sendHtmlMail(newUser.getEmail(),TITLE,content));
//        }
        //发邮件保存信息到数据库
        SysRegisterEmail sysRegisterEmail = new SysRegisterEmail();
        sysRegisterEmail.setContent(content);
        sysRegisterEmail.setStatus(EmailSendStatus.EMAIL_VALIDATION_ING);
        sysRegisterEmail.setSysUserId(newUser.getId());
        sysRegisterEmail.setTitle(TITLE);
        sysRegisterEmail.setVerifyCode(verifyCode);
        sysRegisterEmailRepository.save(sysRegisterEmail);
        return ResponseContent.buildSuccess();
    }

    public ResponseContent activation(String verifyCode){
        SysRegisterEmail sysRegisterEmail = sysRegisterEmailRepository.findByVerifyCodeAndDeletedIsFalse(verifyCode);
        sysRegisterEmail.setStatus(EmailSendStatus.EMAIL_VALIDATION_SUCCEE);
        sysRegisterEmailRepository.save(sysRegisterEmail);
        return ResponseContent.buildSuccess();
    }

}
