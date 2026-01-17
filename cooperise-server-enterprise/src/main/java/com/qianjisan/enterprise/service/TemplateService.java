package com.qianjisan.enterprise.service;

import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Template;
import com.qianjisan.enterprise.request.TemplateQueryRequest;
import com.qianjisan.enterprise.request.TemplateRequest;
import com.qianjisan.enterprise.vo.TemplateVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 事项表 服务类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
public interface TemplateService extends IService<Template> {

    /**
     * 创建模板
     */
    void createTemplate(TemplateRequest request);

    /**
     * 更新模板
     */
    void updateTemplate(Long id, TemplateRequest request);

    /**
     * 删除模板
     */
    void deleteTemplate(Long id);

    /**
     * 根据ID查询模板
     */
    TemplateVo getTemplateById(Long id);

    /**
     * 分页查询模板
     */
    PageVO<TemplateVo> getTemplatePage(TemplateQueryRequest request);
}
