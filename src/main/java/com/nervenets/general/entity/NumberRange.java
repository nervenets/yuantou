package com.nervenets.general.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberRange implements Serializable {
    @ApiModelProperty(value = "最小值")
    private long start;
    @ApiModelProperty(value = "最大值")
    private long end;

    public boolean in(long value) {
        return value >= start && value <= end;
    }

    public boolean isValid() {
        return start > 0 || end > 0;
    }
}
