package com.entertainment.asset.dao.sys;

import com.entertainment.asset.entity.sys.TbUser;
import com.entertainment.common.base.BaseEntityRepository;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: TbUserRepository
 *  @package: com.entertainment.asset.dao.sys
 *  @Date: Created in 2018/10/9 下午5:42
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface TbUserRepository extends BaseEntityRepository<TbUser>{


    TbUser findByDeletedIsFalseAndPhoneNum(String phoneNum);
}
