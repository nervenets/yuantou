package com.nervenets.general.model;

import com.alibaba.fastjson.JSONObject;
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
public class GeneralUser extends GeneralModel {
    @ApiModelProperty(value = "账号")
    private String mobile;
    @ApiModelProperty(value = "昵称")
    private String name;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "微信unionId")
    private String unionId;
    @ApiModelProperty(value = "性别：male男, female女")
    private Gender gender;
    @ApiModelProperty(value = "是否启用")
    private boolean enable = true;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "经度")
    private double longitude = 0;
    @ApiModelProperty(value = "纬度")
    private double latitude = 0;
    @ApiModelProperty(value = "邀请人ID")
    private long inviteUserId;
    @ApiModelProperty(value = "附加内容")
    private JSONObject extra = new JSONObject();
    @ApiModelProperty(value = "微信openIds")
    private JSONObject wxIds = new JSONObject();
}
