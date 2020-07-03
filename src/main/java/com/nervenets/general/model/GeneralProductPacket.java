package com.nervenets.general.model;

import com.nervenets.general.enumeration.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralProductPacket extends GeneralModel {
    @ApiModelProperty(value = "商品ID")
    private long productId;
    @ApiModelProperty(value = "商品类型")
    private ProductType productType;
    @ApiModelProperty(value = "商品分类ID")
    private long productTypeId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "价格ID")
    private long priceId;
    @ApiModelProperty(value = "价格规格阵列组合")
    private List<String> specNames;
    @ApiModelProperty(value = "是否支持库存")
    private boolean stock;
    @ApiModelProperty(value = "价格")
    private int price;
    @ApiModelProperty(value = "数量")
    private int amount;
    @ApiModelProperty(value = "快捷标签")
    private List<String> tags = new ArrayList<>();
}
