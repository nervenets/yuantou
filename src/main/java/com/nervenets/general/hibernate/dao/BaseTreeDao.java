package com.nervenets.general.hibernate.dao;

import com.nervenets.general.hibernate.TreeEntityDomainObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BaseTreeDao<T extends TreeEntityDomainObject> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    List<T> findAllByParentIdOrderByQueueDesc(long parentId);

    long countByParentId(long parentId);

    List<T> findAllByParentIdsLike(String id);
}
