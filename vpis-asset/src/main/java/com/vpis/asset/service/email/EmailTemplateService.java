//package com.vpis.asset.service.email;
//
//import com.vpis.asset.annotation.CachePutAnnotation;
//import com.vpis.asset.annotation.CacheReadAnnotation;
//import com.vpis.asset.dao.sys.EmailTemplateRepository;
//import com.vpis.asset.entity.sys.EmailTemplate;
//import com.vpis.common.type.EmailType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//
///**
// *  @Author: Yuhan.Tang
// *  @ClassName: EmailTemplateService
// *  @package: com.entertainment.asset.service.email
// *  @Date: Created in 2018/8/3 下午4:36
// *  @email yuhan.tang@magicwindow.cn
// *  @Description:
// */
//@Service
//public class EmailTemplateService {
//
//    @Autowired
//    private EmailTemplateRepository emailTemplateRepository;
//
//
//    /**********************使用redis缓存必须使用spring 代理 start**********************/
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    private static final String CACHE_KEY = "EMAIL_TEMPLATE";
//
//    protected EmailTemplateService getSpringProxy() {
//        return applicationContext.getBean(this.getClass());
//    }
//
//    public EmailTemplate getTemplateByType(EmailType type){
//        return this.getSpringProxy().getTemplateByType(CACHE_KEY,type);
//    }
//    /**********************使用redis缓存必须使用spring 代理 start**********************/
//
//    @CacheReadAnnotation
//    private EmailTemplate getTemplateByType(String ikey, EmailType type){
//        return synTemplateByType(type);
//    }
//
//    @CachePutAnnotation
//    private EmailTemplate synTemplateByType(EmailType type){
//        return findTemplateByTypeFromDB(type);
//    }
//
//    private EmailTemplate findTemplateByTypeFromDB(EmailType type){
//        return emailTemplateRepository.findByTypeAndDeletedIsFalse(type);
//    }
//
//
//}
