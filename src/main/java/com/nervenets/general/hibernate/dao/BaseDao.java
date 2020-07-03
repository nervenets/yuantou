package com.nervenets.general.hibernate.dao;

import com.nervenets.general.hibernate.DomainObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseDao<T extends DomainObject> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
