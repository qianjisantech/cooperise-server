package com.dcp.controller;

import com.dcp.common.Result;
import com.dcp.rbac.entity.SysUser;
import com.dcp.rbac.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "用户管理(旧)", description = "用户相关接口")
@RestController("legacyUserController")
@RequestMapping("/user")
public class UserController {

    private final ISysUserService userService;

    public UserController(@Qualifier("legacyUserServiceImpl") ISysUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "获取用户列表（仅包含 id, username, userCode, email）")
    @GetMapping("/list")
    public Result<List<SysUser>> list() {
        // 只查询未被逻辑删除的用户，且只返回需要的字段
        List<SysUser> sysUserList = userService.getSimpleUserList();
        return Result.success(sysUserList);
    }
}
