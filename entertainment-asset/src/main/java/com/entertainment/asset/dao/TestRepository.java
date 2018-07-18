package com.entertainment.asset.dao;

import com.entertainment.common.base.BaseEntityRepository;
import com.entertainment.common.entity.Test;

public interface TestRepository extends BaseEntityRepository<Test>{

    Test findByMediaAdKeyAndDeletedIsFalse(String mediaAdKey);
}
