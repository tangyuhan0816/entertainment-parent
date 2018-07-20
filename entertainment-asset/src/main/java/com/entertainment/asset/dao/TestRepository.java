package com.entertainment.asset.dao;

import com.entertainment.asset.entity.Test;
import com.entertainment.common.base.BaseEntityRepository;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: TestRepository
 *  @package: com.entertainment.asset.dao
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public interface TestRepository extends BaseEntityRepository<Test>{

    Test findByMediaAdKeyAndDeletedIsFalse(String mediaAdKey);
}
