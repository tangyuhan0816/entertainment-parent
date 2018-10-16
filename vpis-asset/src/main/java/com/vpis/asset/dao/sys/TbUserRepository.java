package com.vpis.asset.dao.sys;

import com.vpis.asset.entity.sys.TbUser;
import com.vpis.common.base.BaseEntityRepository;
import com.vpis.common.type.sys.UserTypeEnum;

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

    /**
     * 查询区域管理员
     * @param agentArea
     * @return
     */
    TbUser findByDeletedIsFalseAndUserTypeAndAgentArea(UserTypeEnum userTypeEnum ,String agentArea);
}
