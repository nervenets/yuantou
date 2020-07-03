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
public class GeneralServer extends GeneralModel {
    @ApiModelProperty(value = "服务器名称")
    private String name;
    @ApiModelProperty(value = "域名")
    private String host;
}
