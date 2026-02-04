package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * EnterpriseMember 返回体 (VO)
 */
@Data
public class EnterpriseMemberVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long enterpriseId;

    private Long userId;

    private String role;

    private Integer status;

    private String position;

    private String department;

    private LocalDateTime joinTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}