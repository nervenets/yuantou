package com.nervenets.general.model;

import com.nervenets.general.entity.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralStore extends GeneralModel {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "行业ID")
    private int industryId;
    @ApiModelProperty(value = "地址信息")
    private Location location;
    @ApiModelProperty(value = "连锁ID")
    private long chainId;
    @ApiModelProperty(value = "连锁名称")
    private String chainName;
    @ApiModelProperty(value = "订单服务器ID")
    private long orderServerId;
}
