package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.EmailTemplate;
import com.entertainment.asset.entity.sys.SysRegisterEmail;
import com.entertainment.common.base.BaseEntityRepository;
import com.entertainment.common.type.EmailType;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysRegisterEmailRepository
 *  @package: com.entertainment.asset.dao.sys
 *  @Date: Created in 2018/8/3 下午4:31
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public interface EmailTemplateRepository extends BaseEntityRepository<EmailTemplate>{

    /**
     * 根据邮箱查找用户
     * @param type
     * @return
     */
    EmailTemplate findByTypeAndDeletedIsFalse(EmailType type);

}
