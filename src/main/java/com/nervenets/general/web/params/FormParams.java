package com.nervenets.general.web.params;

import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.service.BaseService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class FormParams<T extends DomainObject, Service extends BaseService<T>> implements Params {
    @ApiModelProperty("实体ID，不传时为新建")
    private long formId;

    public abstract T validateAndBuild(Service service);
}
