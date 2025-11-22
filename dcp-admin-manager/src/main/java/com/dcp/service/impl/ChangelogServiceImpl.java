package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.SysChangelog;
import com.dcp.mapper.ChangelogMapper;
import com.dcp.service.IChangelogService;
import org.springframework.stereotype.Service;

/**
 * Changelog服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class ChangelogServiceImpl extends ServiceImpl<ChangelogMapper, SysChangelog> implements IChangelogService {

}
