package com.nervenets.general.web;

import com.nervenets.general.jwt.aspect.JwtSecurity;
import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.model.MenuRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2020/6/10 16:32 created by Joe
 **/
@Api(value = "权限标识接口", tags = "权限标识类接口")
@RestController
@RequestMapping("/auth/signs")
@Slf4j
public class AuthoritySignController extends BaseController {
    @ApiOperation(value = "权限列表明细", response = MenuRole.class)
    @PostMapping
    @JwtSecurity(required = false)
    public ResponseEntity<?> get() {
        return successMessage(JwtUtils.getAllMenuRoles());
    }
}
