package com.nervenets.general.service.impl;

import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.model.SecurityUser;
import com.nervenets.general.service.BaseService;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<T extends DomainObject, Dao extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>> implements BaseService<T> {
    @Autowired
    public Dao dao;

    @Transactional
    @Override
    public T save(T var1) {
        SecurityUser user = JwtUtils.getUser();
        if (var1.isNew() && null != user) var1.setCreateBy(user.getId());
        try {
            return dao.saveAndFlush(var1);
        } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException | TransactionSystemException e) {
            throw new NerveNetsGeneralException("操作对象已发生改变，请刷新页面重试");
        }
    }

    @Transactional
    @Override
    public List<T> saveAll(List<T> vars) {
        try {
            return dao.saveAll(vars);
        } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException | TransactionSystemException e) {
            throw new NerveNetsGeneralException("操作对象已发生改变，请刷新页面重试");
        }
    }

    @Override
    public Optional<T> findById(long var1) {
        return dao.findById(var1);
    }

    @Override
    public T findOne(long var1) {
        return dao.findById(var1).orElse(null);
    }

    @Override
    public List<T> findAllById(Iterable<Long> ids) {
        return dao.findAllById(ids);
    }

    @Override
    public void deleteById(long var1) {
        dao.deleteById(var1);
    }

    @Override
    public void delete(T var1) {
        dao.delete(var1);
    }

    @Transactional
    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        dao.deleteAll(entities);
    }

    @Override
    public List<T> findAll(Specification<T> var1) {
        return dao.findAll(var1);
    }

    @Override
    public List<T> findAll(Specification<T> var1, Sort var2) {
        return dao.findAll(var1, var2);
    }

    @Override
    public Page<T> findAll(Specification<T> var1, Pageable var2) {
        return dao.findAll(var1, var2);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public long count(Specification<T> specification) {
        return dao.count(specification);
    }

    @Override
    public boolean hasUniqueKey(Long id, String field, String value) {
        List<Long> results = findAll((Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), 0));
            predicates.add(criteriaBuilder.equal(root.get(field), value));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }).stream().map(T::getId).collect(Collectors.toList());
        return !results.contains(id);
    }
}
