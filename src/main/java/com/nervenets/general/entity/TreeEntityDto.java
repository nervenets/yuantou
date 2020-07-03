package com.nervenets.general.entity;

import com.nervenets.general.model.GeneralModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 2020/7/1 16:03 created by Joe
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeEntityDto extends GeneralModel {
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("上级ID")
    private long parentId;
    @ApiModelProperty("是否为叶子节点")
    private boolean leaf = false;
    @ApiModelProperty("上级IDs")
    private List<Long> parentIds;
    @ApiModelProperty("排序")
    private int queue;
    @ApiModelProperty("是否启用")
    private boolean enable;
    @ApiModelProperty("下级列表")
    private List<TreeEntityDto> children;

    public void addChild(TreeEntityDto child) {
        if (null == this.children) this.children = new ArrayList<>();
        this.children.add(child);
    }
}
