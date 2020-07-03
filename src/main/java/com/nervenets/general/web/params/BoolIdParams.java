package com.nervenets.general.web.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoolIdParams extends IdParams {
    @ApiModelProperty(value = "是、否，默认否")
    private boolean yes;
}
