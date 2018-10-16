package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.common.base.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysUserRepository
 *  @package: com.entertainment.asset.dao.sys
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public interface SysUserRepository extends BaseEntityRepository<SysUser>{

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    SysUser findByEmailAndDeletedIsFalse(String email);

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<SysUser> findAllByDeletedIsFalse(Pageable pageable);
}
