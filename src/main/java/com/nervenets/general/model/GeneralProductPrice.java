package com.nervenets.general.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralProductPrice extends GeneralModel {
    @ApiModelProperty(value = "规格阵列组合")
    private List<String> specNames;
    @ApiModelProperty(value = "价格")
    private int price;
    @ApiModelProperty(value = "原价格")
    private int originalPrice;
    @ApiModelProperty(value = "是否开启库存")
    private boolean stock;
}
