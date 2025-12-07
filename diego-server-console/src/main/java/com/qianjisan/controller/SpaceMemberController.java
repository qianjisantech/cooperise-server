package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.Result;
import com.qianjisan.common.dto.SpaceMemberQueryDTO;
import com.qianjisan.request.SpaceMemberRequest;
import com.qianjisan.system.entity.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 空间成员管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "空间成员管理", description = "SpaceMember相关接口")
@RestController
@RequestMapping("/space/member")
@RequiredArgsConstructor
@Slf4j
public class SpaceMemberController {

    private final ISpaceMemberService spaceMemberService;

    @Operation(summary = "添加空间成员")
    @PostMapping
    public Result<Void> create(@RequestBody SpaceMemberRequest request) {
        log.info("[添加空间成员] 请求参数: {}", request);
        spaceMemberService.addMember(request);
        return Result.success();
    }

    @Operation(summary = "更新空间成员")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SpaceMemberRequest request) {
        log.info("[更新空间成员] ID: {}, 请求参数: {}", id, request);
        spaceMemberService.updateMember(id, request);
        return Result.success();
    }

    @Operation(summary = "删除空间成员（物理删除）")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("[删除空间成员] ID: {}", id);
        spaceMemberService.deleteMember(id);
        return Result.success();
    }

    @Operation(summary = "分页查询空间成员（带用户信息）")
    @PostMapping("/page")
    public Result<Page<SpaceMemberVO>> page(@RequestBody SpaceMemberQueryDTO query) {
        log.info("[分页查询空间成员] 查询参数: {}", query);
        Page<SpaceMemberVO> page = spaceMemberService.pageWithUserInfo(query);
        return Result.success(page);
    }

    @Operation(summary = "获取可添加的用户列表")
    @GetMapping("/available-users/{spaceId}")
    public Result<List<SysUser>> getAvailableUsers(@PathVariable Long spaceId) {
        log.info("[获取可添加的用户列表] 空间ID: {}", spaceId);
        List<SysUser> users = spaceMemberService.getAvailableUsers(spaceId);
        return Result.success(users);
    }
}
