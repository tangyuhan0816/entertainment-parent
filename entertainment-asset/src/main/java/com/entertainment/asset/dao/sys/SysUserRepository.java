package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.common.base.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserRepository extends BaseEntityRepository<SysUser>{

    SysUser findByEmailAndDeletedIsFalse(String email);

    Page<SysUser> findAllByDeletedIsFalse(Pageable pageable);
}
