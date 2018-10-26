package com.vpis.asset.repository.house;

import com.vpis.common.base.BaseEntityRepository;
import com.vpis.common.entity.house.Houses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HousesRepository
 *  @package: com.vpis.asset.repository.house
 *  @Date: Created in 2018/10/26 上午11:55
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */    
public interface HousesRepository extends BaseEntityRepository<Houses> {

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<Houses> findByDeletedIsFalse(Pageable pageable);

    @Override
    List<Houses> findAll();
}
