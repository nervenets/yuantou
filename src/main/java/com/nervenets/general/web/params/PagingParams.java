package com.nervenets.general.web.params;

import com.nervenets.general.hibernate.DomainObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class PagingParams<T extends DomainObject> implements Params {
    @ApiModelProperty(value = "页码", required = true)
    @PositiveOrZero(message = "页码无效，必须为大于等于0的正整数")
    private int page;
    @ApiModelProperty(value = "每页数量", required = true)
    @Positive(message = "每页数量必须为大于0的正整数")
    @Max(value = 30)
    private int limit = 10;

    public abstract void generateSpecification(Root<T> root, List<Predicate> predicates, CriteriaQuery<?> query, CriteriaBuilder builder);

    public List<Sort.Order> orders() {
        return new ArrayList<Sort.Order>() {{
            add(new Sort.Order(Sort.Direction.DESC, "id"));
        }};
    }
}
