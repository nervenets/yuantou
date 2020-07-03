package com.nervenets.general.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRole implements Serializable {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "标识")
    private String code;
    @ApiModelProperty(value = "下级")
    private List<MenuRole> children;

    public MenuRole(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public MenuRole add(MenuRole menu) {
        if (null == this.children) this.children = new ArrayList<>();
        this.children.add(menu);
        return this;
    }

    public boolean contains(MenuRole menu) {
        if (null == this.children) return false;
        return this.children.contains(menu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuRole menuRole = (MenuRole) o;
        return id.equals(menuRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
