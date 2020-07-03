package com.nervenets.general.service.impl;

import com.nervenets.general.entity.TreeEntityDto;
import com.nervenets.general.hibernate.TreeEntityDomainObject;
import com.nervenets.general.hibernate.dao.BaseTreeDao;
import com.nervenets.general.service.TreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 2020/6/30 12:09 created by Joe
 **/
@Slf4j
public class TreeServiceImpl<T extends TreeEntityDomainObject<? extends TreeEntityDto>, Dao extends BaseTreeDao<T>> extends BaseServiceImpl<T, Dao> implements TreeService<T> {
    @Transactional
    @Override
    @CacheEvict(value = "trees", key = "#root.targetClass.name")
    public T save(T entity) {
        List<Long> oldParentIds = entity.getParentIds();
        if (entity.getParentId() > 0) {
            T parent = dao.findById(entity.getParentId()).orElse(null);
            if (parent != null && parent.getId() > 0) {
                parent.setLeaf(false);
                super.save(parent);

                List<Long> parentIds = parent.getParentIds();
                if (null == parentIds) parentIds = new ArrayList<>();
                parentIds.add(0, parent.getId());
                entity.setParentIds(parentIds);
            }
        }

        boolean isNewEntity = entity.isNew();
        super.save(entity);

        if (isNewEntity) {
            long count = dao.countByParentId(entity.getId());
            entity.setLeaf(count == 0);
        } else {
            entity.setLeaf(true);
        }
        super.save(entity);

        if (null != oldParentIds && !oldParentIds.isEmpty()) {
            String oldParents = StringUtils.join(",", oldParentIds);
            String newParents = StringUtils.join(",", entity.getParentIds());

            List<T> list = dao.findAllByParentIdsLike(String.valueOf(entity.getId()));
            for (T e : list) {
                if (null != e.getParentIds() && !e.getParentIds().isEmpty()) {
                    String nowParents = StringUtils.join(",", e.getParentIds());
                    String results = nowParents.replace(oldParents, newParents);
                    String[] strings = results.split(",");
                    List<Long> eNewParents = new ArrayList<>();
                    for (String v : strings) {
                        eNewParents.add(Long.valueOf(v));
                    }
                    e.setParentIds(eNewParents);
                }
            }
            if (!list.isEmpty()) {
                super.saveAll(list);
            }
        }

        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "trees", key = "#root.targetClass.name")
    public void delete(T var1) {
        super.delete(var1);
    }

    @Override
    @CacheEvict(value = "trees", key = "#root.targetClass.name")
    public void deleteById(long var1) {
        super.deleteById(var1);
    }

    @Override
    @CacheEvict(value = "trees", key = "#root.targetClass.name")
    public void deleteAll(Iterable<? extends T> entities) {
        super.deleteAll(entities);
    }

    @Override
    public List<T> findParentsById(long id) {
        List<T> list = new ArrayList<>();

        long tempId = id;

        T byId = findById(tempId).orElse(null);
        while (null != byId) {
            list.add(0, byId);

            tempId = byId.getParentId();
            if (0 == tempId) {
                break;
            }
            byId = findById(tempId).orElse(null);
        }

        return list;
    }

    @Override
    public List<T> findAllByParentId(long parentId) {
        return dao.findAllByParentIdOrderByQueueDesc(parentId);
    }

    @Override
    @Cacheable(value = "trees", key = "#root.targetClass.name", sync = true)
    public List<TreeEntityDto> getTrees() {
        List<TreeEntityDto> dtos = new ArrayList<>();

        List<T> sources = findAllByParentId(0);
        sources.forEach(t -> {
            if (!t.isDeleted() && t.isEnable()) {
                TreeEntityDto dto = t.toDto();
                childrenAdd(dto);
                dtos.add(dto);
            }
        });
        return dtos;
    }

    private void childrenAdd(TreeEntityDto dto) {
        final List<T> children = findAllByParentId(dto.getId());
        for (T child : children) {
            if (!child.isDeleted() && child.isEnable()) {
                TreeEntityDto childDto = child.toDto();
                childrenAdd(childDto);
                dto.addChild(childDto);
            }
        }
    }
}
