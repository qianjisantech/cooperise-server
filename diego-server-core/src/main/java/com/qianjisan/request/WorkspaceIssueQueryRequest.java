package com.qianjisan.request;

import com.qianjisan.common.dto.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WorkspaceIssueQueryRequest extends PageQuery {

    /**
     * 事项单号
     */
    private String issueNo;

    /**
     * 标题
     */
    private  String summary;
}
