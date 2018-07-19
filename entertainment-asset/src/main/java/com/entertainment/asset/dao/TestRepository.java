package com.entertainment.asset.dao;

import com.entertainment.asset.entity.Test;
import com.entertainment.common.base.BaseEntityRepository;

public interface TestRepository extends BaseEntityRepository<Test>{

    Test findByMediaAdKeyAndDeletedIsFalse(String mediaAdKey);
}
