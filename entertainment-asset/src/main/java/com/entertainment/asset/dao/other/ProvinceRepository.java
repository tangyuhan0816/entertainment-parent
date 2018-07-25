package com.entertainment.asset.dao.other;

import com.entertainment.asset.entity.other.Province;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

public interface ProvinceRepository extends BaseEntityRepository<Province>{

    List<Province> findBy();
}
