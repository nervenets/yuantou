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
public class GeneralCustomer extends GeneralModel {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "工作岗位")
    private String job;
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    @ApiModelProperty(value = "父级ID，店主的为0，店员的为店主的ID")
    private long parentId;
}
