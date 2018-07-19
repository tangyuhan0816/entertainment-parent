package com.entertainment.asset.service.sys;

import com.entertainment.asset.dao.sys.SysUserRepository;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.base.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService extends BaseCacheService<SysUser>{

    @Autowired
    private SysUserRepository sysUserRepository;

    public SysUser findSysUserByEmail(String email){
        return sysUserRepository.findByEmailAndDeletedIsFalse(email);
    }
}
