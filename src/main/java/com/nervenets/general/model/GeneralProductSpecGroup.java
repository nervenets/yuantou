package com.nervenets.general.model;

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
public class GeneralProductSpecGroup extends GeneralModel {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "排序值")
    private int queue;
    @ApiModelProperty(value = "商品规格")
    private List<GeneralProductSpec> specs = new ArrayList<>();

    public GeneralProductSpec findSpec(long specId) {
        for (GeneralProductSpec spec : specs) {
            if (spec.getId() == specId) return spec;
        }
        return null;
    }
}
