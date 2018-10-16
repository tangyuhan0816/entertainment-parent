package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.SysRegisterEmail;
import com.entertainment.common.base.BaseEntityRepository;
import com.entertainment.common.type.EmailSendStatus;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysRegisterEmailRepository
 *  @package: com.entertainment.asset.dao.sys
 *  @Date: Created in 2018/8/3 下午4:31
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public interface SysRegisterEmailRepository extends BaseEntityRepository<SysRegisterEmail>{

    /**
     * 根据邮箱查找用户
     * @param verifyCode
     * @return
     */
    SysRegisterEmail findByVerifyCodeAndDeletedIsFalse(String verifyCode);

}
