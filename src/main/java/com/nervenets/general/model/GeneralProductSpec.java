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
public class GeneralProductSpec extends GeneralModel {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "是否启用")
    private boolean enable;
}
