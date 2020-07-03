/*
 * Copyright (c) 2007 IJO Technologies Ltd.
 * www.ijotechnologies.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * IJO Technologies ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with IJO Technologies.
 */

package com.nervenets.general.hibernate;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nervenets.general.utils.JodaUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class DomainObject implements Serializable {
    @Version
    @Column(name = "v")
    @ApiModelProperty(value = "对象版本号(被更新次数)")
    @JsonIgnore
    @JSONField(serialize = false)
    protected int version = 0;
    @Column(name = "del")
    @ApiModelProperty(value = "删除时间，为0时未被删除")
    @JsonIgnore
    @JSONField(serialize = false)
    private int deleted = 0;
    @ApiModelProperty(value = "ID")
    @Id
    @TableGenerator(name = "tableGenerator", initialValue = 111111, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long id;
    @Index(name = "_idx_ct")
    @Column(name = "ct")
    @ApiModelProperty(value = "创建时间")
    private int createTime = JodaUtils.getTimestamp();
    @ApiModelProperty(value = "创建人")
    private long createBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainObject)) return false;

        DomainObject that = (DomainObject) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isDeleted() {
        return this.deleted > 0;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isNew() {
        return null == id || 0 == id;
    }
}
