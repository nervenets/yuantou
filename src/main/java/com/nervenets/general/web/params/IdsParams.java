package com.nervenets.general.web.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class IdsParams implements Params {
    @ApiModelProperty("目标IDs")
    @NotEmpty
    private List<Long> targetIds;
}
