package com.nervenets.general.web.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class IdParams implements Params {
    @ApiModelProperty("目标ID")
    @Positive(message = "目标ID必须为正整数")
    private long targetId;
}
