package com.nervenets.general.web.params;

import com.nervenets.general.Global;
import com.nervenets.general.entity.MessageCode;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.hibernate.TreeEntityDomainObject;
import com.nervenets.general.service.BaseService;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * 2020/6/30 11:46 created by Joe
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TreeFormParams<T extends TreeEntityDomainObject, Service extends BaseService<T>> extends FormParams<T, Service> {
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    @Size(min = 2, max = 10)
    private String name;
    @ApiModelProperty("上级ID，顶级为0")
    private long parentId;
    @ApiModelProperty("排序")
    @PositiveOrZero
    private int queue;
    @ApiModelProperty("是否启用")
    private boolean enable;

    @Override
    public T validateAndBuild(Service service) {
        T t = instance();
        if (getFormId() > 0) {
            t = service.findById(getFormId()).orElse(null);
            if (null == t) throw new NerveNetsGeneralException(MessageCode.code_404, "您编辑的内容不存在");
        }
        if (parentId > 0) {
            T parent = service.findById(parentId).orElse(null);
            if (null == parent) throw new NerveNetsGeneralException(MessageCode.code_404, "您选择的父级不存在");
        }
        BeanUtils.copyProperties(this, t, Global.defaultIgnoreProperties());
        return t;
    }

    public abstract T instance();
}
