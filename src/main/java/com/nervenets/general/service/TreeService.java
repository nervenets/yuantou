package com.nervenets.general.service;

import com.nervenets.general.entity.TreeEntityDto;
import com.nervenets.general.hibernate.TreeEntityDomainObject;

import java.util.List;

/**
 * 2020/6/30 12:08 created by Joe
 **/
public interface TreeService<T extends TreeEntityDomainObject<? extends TreeEntityDto>> extends BaseService<T> {
    List<T> findParentsById(long id);

    List<T> findAllByParentId(long parentId);

    List<TreeEntityDto> getTrees();
}
