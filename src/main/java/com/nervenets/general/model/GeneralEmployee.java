package com.nervenets.general.model;

import com.nervenets.general.enumeration.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralEmployee extends GeneralModel {
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "性别")
    private Gender gender;
    @ApiModelProperty(value = "职位")
    private String job;
}
