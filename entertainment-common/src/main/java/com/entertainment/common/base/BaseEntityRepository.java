package com.entertainment.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BaseEntityRepository
 *  @package: com.entertainment.common.base
 *  @Date: Created in 2018/7/20 下午5:57
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@NoRepositoryBean
public interface BaseEntityRepository <T extends BaseEntity> extends JpaRepository<T, Long> {

    /**
     * 根据ID查找数据
     * @param id
     * @return
     */
    T findOneById(Long id);
}
