package com.vpis.asset.repository.sys;

import com.vpis.common.base.BaseEntityRepository;
import com.vpis.common.entity.sys.ExtendInfo;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ExtendInfoRepository
 *  @package: com.vpis.asset.dao.sys
 *  @Date: Created in 2018/10/30 下午3:30
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface ExtendInfoRepository extends BaseEntityRepository<ExtendInfo>{

    /**
     * 获取代理商banner图片
     * @param userId
     * @return
     */
    List<ExtendInfo> findByUserIdAndDeletedIsFalse(Long userId);
}
