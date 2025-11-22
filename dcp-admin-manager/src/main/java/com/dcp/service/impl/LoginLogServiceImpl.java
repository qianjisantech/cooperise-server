package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.SysLoginLog;
import com.dcp.mapper.LoginLogMapper;
import com.dcp.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * LoginLog服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, SysLoginLog> implements ILoginLogService {

}
