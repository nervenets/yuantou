package com.nervenets.general.web;

import com.nervenets.general.entity.KeyValue;
import com.nervenets.general.entity.MessageCode;
import com.nervenets.general.entity.ResponseResult;
import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.i18n.Translator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {
    protected ResponseEntity successMessage() {
        return this.successMessage(null);
    }

    protected final ResponseEntity successMessage(Object object, KeyValue... keyValues) {
        ResponseResult responseResult = ResponseResult.success();
        responseResult.setData(object);
        responseResult.setMessage(Translator.translate("code_200"));
        for (KeyValue v : keyValues) {
            responseResult.put(v.getKey(), v.getValue());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseResult);
    }

    protected ResponseEntity errorMessage(int messageCode) {
        return errorMessage(messageCode, Translator.translate("code_" + messageCode));
    }

    protected ResponseEntity errorMessage(String customMessage) {
        return errorMessage(MessageCode.code_400, customMessage);
    }

    protected ResponseEntity errorMessage(int messageCode, Object object) {
        return errorMessage(messageCode, object, Translator.translate("code_" + messageCode));
    }

    protected ResponseEntity errorMessage(int messageCode, String customMessage) {
        return errorMessage(messageCode, null, customMessage);
    }

    protected ResponseEntity errorMessage(int messageCode, Object object, String customMessage) {
        return ResponseEntity.status(messageCode).body(new ResponseResult(messageCode, object, customMessage));
    }

    protected <D extends DomainObject> Specification<D> generateSpecification(SpecificationCallback<D> callback) {
        return (Specification<D>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            callback.apply(root, predicates, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected interface SpecificationCallback<T> {
        void apply(Root<T> root, List<Predicate> predicates, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder);
    }
}
