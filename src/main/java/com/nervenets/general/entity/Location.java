package com.nervenets.general.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    @ApiModelProperty(value = "地区ID")
    private int areaId;
    @ApiModelProperty(value = "详细地址")
    @Column(columnDefinition = "varchar(50)")
    private String detail;
    @ApiModelProperty(value = "经度")
    private double longitude = 0;
    @ApiModelProperty(value = "纬度")
    private double latitude = 0;
}
