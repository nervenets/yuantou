package com.nervenets.general.service;

import com.nervenets.general.hibernate.DomainObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends DomainObject> {
    T save(T var1);

    List<T> saveAll(List<T> vars);

    Optional<T> findById(long var1);

    T findOne(long var1);

    List<T> findAllById(Iterable<Long> ids);

    void deleteById(long var1);

    void delete(T var1);

    void deleteAll(Iterable<? extends T> entities);

    List<T> findAll(@Nullable Specification<T> var1);

    List<T> findAll(@Nullable Specification<T> var1, Sort var2);

    Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);

    long count();

    long count(Specification<T> specification);

    boolean hasUniqueKey(Long id, String field, String value);
}
