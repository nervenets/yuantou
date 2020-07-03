package com.nervenets.general.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class GeneralModel implements Serializable {
    @ApiModelProperty(value = "ID")
    private long id;
    @ApiModelProperty(value = "创建时间")
    private int createTime;
    @ApiModelProperty(value = "创建人")
    private long createBy;
    @ApiModelProperty(value = "删除时间")
    private int deleted;
}
