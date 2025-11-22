package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.SysUserSettings;
import com.dcp.mapper.UserSettingsMapper;
import com.dcp.service.IUserSettingsService;
import org.springframework.stereotype.Service;

/**
 * UserSettings服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, SysUserSettings> implements IUserSettingsService {

}
