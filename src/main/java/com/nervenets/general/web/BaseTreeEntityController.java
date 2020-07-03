package com.nervenets.general.web;

import com.nervenets.general.entity.TreeEntityDto;
import com.nervenets.general.enumeration.Action;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.hibernate.TreeEntityDomainObject;
import com.nervenets.general.jwt.aspect.JwtSecurity;
import com.nervenets.general.service.TreeService;
import com.nervenets.general.web.params.BoolIdParams;
import com.nervenets.general.web.params.IdParams;
import com.nervenets.general.web.params.PagingParams;
import com.nervenets.general.web.params.TreeFormParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 2020/6/30 11:43 created by Joe
 **/
@Slf4j
public abstract class BaseTreeEntityController<T extends TreeEntityDomainObject<? extends TreeEntityDto>, Service extends TreeService<T>, F extends TreeFormParams<T, Service>, P extends PagingParams<T>> extends BaseEntityController<T, Service, F, P> {

    @ApiOperation(value = "禁用启用")
    @PostMapping("/enable")
    @JwtSecurity(permission = "enable", permissionName = "禁用启用")
    public ResponseEntity<?> enable(@Valid @RequestBody BoolIdParams params) {
        T t = service.findById(params.getTargetId()).orElse(null);
        if (null == t || t.isDeleted()) throw new NerveNetsGeneralException(404, "您要删除的数据不存在");
        t.setEnable(params.isYes());
        service.save(t);
        return successMessage(t);
    }

    @ApiOperation(value = "树")
    @PostMapping("/trees")
    public ResponseEntity<?> trees() {
        return successMessage(service.getTrees());
    }

    @ApiOperation(value = "下级列表")
    @PostMapping("/children")
    public ResponseEntity<?> children(@Valid @RequestBody IdParams params) {
        return successMessage(service.findAllByParentId(params.getTargetId()));
    }

    @ApiOperation(value = "父级列表")
    @PostMapping("/parents")
    public ResponseEntity<?> parents(@Valid @RequestBody IdParams params) {
        return successMessage(service.findParentsById(params.getTargetId()));
    }
}
