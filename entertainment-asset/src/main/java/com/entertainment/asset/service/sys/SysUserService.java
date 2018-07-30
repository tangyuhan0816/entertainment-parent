package com.entertainment.asset.service.sys;

import com.entertainment.asset.dao.sys.SysUserRepository;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.base.BaseCacheService;
import com.entertainment.common.page.PageableRequest;
import com.entertainment.common.page.PageableResponse;
import com.entertainment.common.utils.PageableConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class SysUserService extends BaseCacheService<SysUser>{

    @Autowired
    private SysUserRepository sysUserRepository;

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
}
