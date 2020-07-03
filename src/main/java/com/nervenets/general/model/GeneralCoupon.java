package com.nervenets.general.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralCoupon extends GeneralModel {
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "门店ID")
    private long storeId;
    @ApiModelProperty(value = "创建员工")
    private long createEmployeeId;
    @ApiModelProperty(value = "券面价值")
    private int value;
    @ApiModelProperty(value = "优惠券价格")
    private int price;
    @ApiModelProperty(value = "优惠券原价")
    private int originalPrice;
}
