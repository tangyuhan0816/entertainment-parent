package com.entertainment.common.base;

import com.entertainment.common.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEntityRepository <T extends BaseEntity> extends JpaRepository<T, Long> {

    T findOneById(Long id);
}
