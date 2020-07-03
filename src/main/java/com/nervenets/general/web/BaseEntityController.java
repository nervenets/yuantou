package com.nervenets.general.web;

import com.nervenets.general.entity.KeyValue;
import com.nervenets.general.enumeration.Action;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.jwt.aspect.JwtSecurity;
import com.nervenets.general.service.BaseService;
import com.nervenets.general.utils.JodaUtils;
import com.nervenets.general.web.params.FormParams;
import com.nervenets.general.web.params.IdParams;
import com.nervenets.general.web.params.PagingParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * 实体基础 BaseEntityController
 *
 * @param <T>       实体类
 * @param <Service> 实体服务类
 * @param <F>       实体表单参数类
 * @param <P>       实体分页参数数
 */
@Slf4j
public abstract class BaseEntityController<T extends DomainObject, Service extends BaseService<T>, F extends FormParams<T, Service>, P extends PagingParams<T>> extends BaseController {
    @Autowired
    public Service service;

    /**
     * action操作之前的功能及拦截，默认什么都不做
     *
     * @param action 操作类型
     * @param t      操作对象
     */
    protected void before(Action action, T t) {
        //nothing to do ...
    }

    /**
     * action操作之后的功能及拦截，默认什么都不做
     *
     * @param action 操作类型
     * @param t      操作对象
     */
    protected void after(Action action, T t) {
        //nothing to do ...
    }

    /**
     * 构建列表返回对象，默认返回源对象
     *
     * @param t 源对象
     * @return 返回对象
     */
    protected Object generateListResult(T t) {
        return t;
    }

    /**
     * 构建列表返回列表对象，默认返回源对象
     *
     * @param page 源对象列表
     * @return 返回对象列表
     */
    protected Object generateListResults(Page<T> page) {
        return page.get().map(this::generateListResult).collect(Collectors.toList());
    }

    /**
     * 构建详情返回对象，默认返回源对象
     *
     * @param t 源对象
     * @return 返回对象
     */
    protected Object generateDetailResult(T t) {
        return t;
    }

    @ApiOperation(value = "编辑")
    @PostMapping("/edit")
    @JwtSecurity(permission = "edit", permissionName = "编辑")
    public ResponseEntity<T> edit(@Valid @RequestBody F params) {
        T entity = params.validateAndBuild(service);
        boolean isNew = 0 == params.getFormId();
        before(isNew ? Action.add : Action.edit, entity);
        T domain = service.save(entity);
        after(isNew ? Action.add : Action.edit, domain);
        return successMessage(domain);
    }

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    @JwtSecurity(permission = "list", permissionName = "列表")
    public ResponseEntity<T> list(@Valid @RequestBody P params) {
        Page<T> page = service.findAll(generateSpecification((root, predicates, criteriaQuery, criteriaBuilder) -> {
            params.generateSpecification(root, predicates, criteriaQuery, criteriaBuilder);
            predicates.add(criteriaBuilder.equal(root.get("deleted"), 0));
        }), PageRequest.of(params.getPage(), params.getLimit(), Sort.by(params.orders())));
        return successMessage(generateListResults(page), new KeyValue("page", params.getPage()), new KeyValue("total", page.getTotalElements()));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/del")
    @JwtSecurity(permission = "del", permissionName = "删除")
    public ResponseEntity<Void> del(@Valid @RequestBody IdParams params) {
        T t = service.findById(params.getTargetId()).orElse(null);
        if (null == t || t.isDeleted()) throw new NerveNetsGeneralException(404, "您要删除的数据不存在");
        before(Action.delete, t);
        t.setDeleted(JodaUtils.getTimestamp());
        service.save(t);
        after(Action.delete, t);
        return successMessage();
    }

    @ApiOperation(value = "查看")
    @PostMapping("/detail")
    @JwtSecurity(permission = "detail", permissionName = "查看")
    public ResponseEntity<T> detail(@Valid @RequestBody IdParams params) {
        T t = service.findById(params.getTargetId()).orElse(null);
        if (null == t || t.isDeleted()) throw new NerveNetsGeneralException(404, "您要查看的数据不存在");
        return successMessage(generateDetailResult(t));
    }
}
