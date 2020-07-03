package com.nervenets.general.hibernate;

import com.nervenets.general.adapter.jsonType.LongArrayJson;
import com.nervenets.general.entity.TreeEntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;

/**
 * 2020/6/30 11:36 created by Joe
 **/
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class TreeEntityDomainObject<T extends TreeEntityDto> extends DomainObject {
    @ApiModelProperty("名称")
    @Column(columnDefinition = "varchar(20)")
    private String name;
    @ApiModelProperty("上级ID")
    private long parentId;
    @ApiModelProperty("是否为叶子节点")
    private boolean leaf = false;
    @ApiModelProperty("上级IDs")
    @Type(type = LongArrayJson.NAME)
    @Column(columnDefinition = "varchar(1000)")
    private List<Long> parentIds;
    @ApiModelProperty("排序")
    private int queue;
    @ApiModelProperty("是否启用")
    private boolean enable = true;

    public abstract T toDto();
}
