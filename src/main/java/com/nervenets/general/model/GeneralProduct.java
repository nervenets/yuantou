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
public class GeneralProduct extends GeneralModel {
    @ApiModelProperty(value = "门店ID")
    private long storeId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "一句话描述")
    private String slogan;
    @ApiModelProperty(value = "LOGO")
    private String logo;
    @ApiModelProperty(value = "分类ID")
    private long typeId;
    @ApiModelProperty(value = "商品类型")
    private ProductType productType;
    @ApiModelProperty(value = "是否上架")
    private boolean enable = true;
    @ApiModelProperty(value = "快捷标签")
    private List<String> tags = new ArrayList<>();
    @ApiModelProperty(value = "商品规格组")
    private List<GeneralProductSpecGroup> specGroups = new ArrayList<>();
    @ApiModelProperty(value = "打包商品的商品列表")
    private List<GeneralProductPacket> packets;
    @ApiModelProperty(value = "价格列表")
    private List<GeneralProductPrice> prices;

    public GeneralProductSpecGroup findSpecGroup(long specGroupId) {
        for (GeneralProductSpecGroup specGroup : specGroups) {
            if (specGroup.getId() == specGroupId) return specGroup;
        }
        return null;
    }

    public GeneralProductSpec findSpec(long specId) {
        for (GeneralProductSpecGroup specGroup : specGroups) {
            for (GeneralProductSpec spec : specGroup.getSpecs()) {
                if (spec.getId() == specId) return spec;
            }
        }
        return null;
    }

    public GeneralProductPrice findPrice(long priceId) {
        for (GeneralProductPrice price : prices) {
            if (price.getId() == priceId) return price;
        }
        return null;
    }

    public boolean isSingle() {
        return 1 == specGroups.size() && 1 == specGroups.get(0).getSpecs().size();
    }
}
