package com.nervenets.general.entity;

import com.nervenets.general.enumeration.UnionType;
import com.nervenets.general.utils.JodaUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NNLog implements Serializable {
    @ApiModelProperty(value = "时间")
    private String t = JodaUtils.timeLongToString();
    @ApiModelProperty(value = "操作")
    private String a;
    @ApiModelProperty(value = "描述")
    private String d;
    @ApiModelProperty(value = "关联类型")
    private UnionType u;
    @ApiModelProperty(value = "关联ID")
    private Long i;

    public NNLog(String action, String detail, UnionType unionType, Long unionId) {
        this.a = action;
        this.d = detail;
        this.u = unionType;
        this.i = unionId;
    }
}
