package com.nervenets.general.model;

import com.nervenets.general.entity.Location;
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
public class GeneralAddress extends GeneralModel {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private Gender gender;
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    @ApiModelProperty(value = "位置信息")
    private Location location;
    @ApiModelProperty(value = "是否设置为默认")
    private boolean def;
}
