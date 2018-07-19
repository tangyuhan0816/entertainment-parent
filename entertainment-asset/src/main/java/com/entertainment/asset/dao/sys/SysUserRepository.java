package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.common.base.BaseEntityRepository;

public interface SysUserRepository extends BaseEntityRepository<SysUser>{

    SysUser findByEmailAndDeletedIsFalse(String email);

}
