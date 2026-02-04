package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.EnterpriseMemberQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseMemberRequest;
import com.qianjisan.enterprise.service.IEnterpriseMemberService;
import com.qianjisan.enterprise.vo.EnterpriseMemberVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "企业成员", description = "Enterprise Member 相关接口")
@RestController
@RequestMapping("/enterprise-api/enterprise-member")
@RequiredArgsConstructor
@Slf4j
public class EnterpriseMemberController {

    private final IEnterpriseMemberService enterpriseMemberService;

    @Operation(summary = "创建企业成员")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody EnterpriseMemberRequest request) {
        try {
            enterpriseMemberService.createEnterpriseMember(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建企业成员失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新企业成员")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody EnterpriseMemberRequest request) {
        try {
            enterpriseMemberService.updateEnterpriseMember(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新企业成员失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除企业成员")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            enterpriseMemberService.deleteEnterpriseMember(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除企业成员失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询企业成员")
    @GetMapping("/{id}")
    public Result<EnterpriseMemberVo> getById(@PathVariable Long id) {
        try {
            EnterpriseMemberVo vo = enterpriseMemberService.getEnterpriseMemberById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询企业成员失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询企业成员")
    @PostMapping("/page")
    public Result<PageVO<EnterpriseMemberVo>> page(@RequestBody EnterpriseMemberQueryRequest request) {
        try {
            PageVO<EnterpriseMemberVo> pageVO = enterpriseMemberService.getEnterpriseMemberPage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询企业成员失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据企业ID查询成员列表")
    @GetMapping("/enterprise/{enterpriseId}")
    public Result<List<EnterpriseMemberVo>> getMembersByEnterpriseId(@PathVariable Long enterpriseId) {
        try {
            List<EnterpriseMemberVo> vos = enterpriseMemberService.getMembersByEnterpriseId(enterpriseId);
            return Result.success(vos);
        } catch (Exception e) {
            log.error("查询企业成员列表失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据用户ID查询企业成员信息")
    @GetMapping("/user/{userId}")
    public Result<EnterpriseMemberVo> getMemberByUserId(@PathVariable Long userId) {
        try {
            EnterpriseMemberVo vo = enterpriseMemberService.getMemberByUserId(userId);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询用户企业成员信息失败", e);
            return Result.error(e.getMessage());
        }
    }
}