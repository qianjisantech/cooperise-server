package com.dcp.rbac.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 分配角色请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class AssignRoleRequest {

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}
