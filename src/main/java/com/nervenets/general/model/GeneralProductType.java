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
public class GeneralProductType extends GeneralModel {
    @ApiModelProperty(value = "门店ID")
    private long storeId;
    @ApiModelProperty(value = "名称")
    private String name;
}
