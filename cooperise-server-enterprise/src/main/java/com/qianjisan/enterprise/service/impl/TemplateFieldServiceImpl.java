package com.qianjisan.enterprise.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.TemplateField;
import com.qianjisan.enterprise.mapper.TemplateFieldMapper;
import com.qianjisan.enterprise.request.BatchSaveTemplateFieldRequest;
import com.qianjisan.enterprise.request.TemplateFieldQueryRequest;

import com.qianjisan.enterprise.service.TemplateFieldService;
import com.qianjisan.enterprise.vo.TemplateFieldVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 模板字段 服务实现类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Service
@RequiredArgsConstructor
public class TemplateFieldServiceImpl extends ServiceImpl<TemplateFieldMapper, TemplateField> implements TemplateFieldService {

    @Override
    public void batchSaveTemplateFields(BatchSaveTemplateFieldRequest request) {
        List<TemplateField> toSave = new ArrayList<>();
        List<TemplateField> toUpdate = new ArrayList<>();

        // 获取当前用户信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();

        for (BatchSaveTemplateFieldRequest.TemplateFieldRequest fieldRequest : request.getFields()) {
            TemplateField templateField = BeanUtil.copyProperties(fieldRequest, TemplateField.class);

            if (fieldRequest.getId() == null) {
                // 新增
                templateField.setTemplateId(request.getTemplateId());
                templateField.setCreateTime(LocalDateTime.now());
                templateField.setUpdateTime(LocalDateTime.now());

                // 设置创建人信息
                if (userId != null) {
                    templateField.setCreateById(userId);
                    templateField.setCreateByCode(username);
                    templateField.setCreateByName(nickname);
                    templateField.setUpdateById(userId);
                    templateField.setUpdateByCode(username);
                    templateField.setUpdateByName(nickname);
                }

                toSave.add(templateField);
            } else {
                // 更新
                templateField.setUpdateTime(LocalDateTime.now());

                // 设置更新人信息
                if (userId != null) {
                    templateField.setUpdateById(userId);
                    templateField.setUpdateByCode(username);
                    templateField.setUpdateByName(nickname);
                }

                toUpdate.add(templateField);
            }
        }

        // 批量保存和更新
        if (!toSave.isEmpty()) {
            this.saveBatch(toSave);
        }
        if (!toUpdate.isEmpty()) {
            this.updateBatchById(toUpdate);
        }
    }


    @Override
    public void updateTemplateField(Long id, BatchSaveTemplateFieldRequest request) {
        TemplateField templateField = this.getById(id);
        if (templateField == null) {
            throw new RuntimeException("模板字段不存在");
        }

        BeanUtil.copyProperties(request, templateField);
        templateField.setUpdateTime(LocalDateTime.now());

        // 设置更新人信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();
        if (userId != null) {
            templateField.setUpdateById(userId);
            templateField.setUpdateByCode(username);
            templateField.setUpdateByName(nickname);
        }

        this.updateById(templateField);
    }

    @Override
    public void deleteTemplateField(Long id) {
        TemplateField templateField = this.getById(id);
        if (templateField == null) {
            throw new RuntimeException("模板字段不存在");
        }

        this.removeById(id);
    }

    @Override
    public TemplateFieldVo getTemplateFieldById(Long id) {
        TemplateField templateField = this.getById(id);
        if (templateField == null) {
            throw new RuntimeException("模板字段不存在");
        }

        return BeanUtil.copyProperties(templateField, TemplateFieldVo.class);
    }

    @Override
    public PageVO<TemplateFieldVo> getTemplateFieldPage(TemplateFieldQueryRequest request) {
        LambdaQueryWrapper<TemplateField> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (StringUtils.hasText(request.getTemplateId())) {
            queryWrapper.eq(TemplateField::getTemplateId, request.getTemplateId());
        }
        if (StringUtils.hasText(request.getFieldName())) {
            queryWrapper.like(TemplateField::getFieldName, request.getFieldName());
        }
        if (StringUtils.hasText(request.getFieldCode())) {
            queryWrapper.like(TemplateField::getFieldCode, request.getFieldCode());
        }
        if (StringUtils.hasText(request.getFieldType())) {
            queryWrapper.eq(TemplateField::getFieldType, request.getFieldType());
        }
        if (request.getIsEdit() != null) {
            queryWrapper.eq(TemplateField::getIsEdit, request.getIsEdit());
        }
        if (request.getIsRequired() != null) {
            queryWrapper.eq(TemplateField::getIsRequired, request.getIsRequired());
        }

        if (StringUtils.hasText(request.getPosition())) {
            queryWrapper.eq(TemplateField::getPosition, request.getPosition());
        }

        // 按排序字段和创建时间排序
        queryWrapper.orderByAsc(TemplateField::getSort)
                .orderByDesc(TemplateField::getCreateTime);

        // 分页查询
        IPage<TemplateField> page = this.page(new Page<>(request.getCurrent(), request.getSize()), queryWrapper);

        // 转换为VO
        PageVO<TemplateFieldVo> pageVO = new PageVO<>();
        pageVO.setRecords(BeanUtil.copyToList(page.getRecords(), TemplateFieldVo.class));
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setPages(page.getPages());

        return pageVO;
    }

    @Override
    public List<TemplateFieldVo> getFieldsByTemplateId(String templateId) {
        LambdaQueryWrapper<TemplateField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TemplateField::getTemplateId, templateId)
                .orderByAsc(TemplateField::getSort);

        List<TemplateField> templateFields = this.list(queryWrapper);
        return BeanUtil.copyToList(templateFields, TemplateFieldVo.class);
    }
}
