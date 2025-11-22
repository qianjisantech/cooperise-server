-- 埋点日志表
CREATE TABLE `tracking_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NULL COMMENT '用户ID',
  `username` VARCHAR(100) NULL COMMENT '用户名',
  `user_code` VARCHAR(100) NULL COMMENT '用户工号',

  `event_type` VARCHAR(50) NOT NULL COMMENT '事件类型: page_view(页面访问), login(登录), logout(登出), button_click(按钮点击), form_submit(表单提交), file_upload(文件上传), custom(自定义)',
  `event_name` VARCHAR(200) NULL COMMENT '事件名称',
  `event_category` VARCHAR(100) NULL COMMENT '事件分类',

  `page_url` VARCHAR(500) NULL COMMENT '页面URL',
  `page_title` VARCHAR(200) NULL COMMENT '页面标题',
  `page_referrer` VARCHAR(500) NULL COMMENT '来源页面',

  `element_id` VARCHAR(200) NULL COMMENT '元素ID',
  `element_class` VARCHAR(200) NULL COMMENT '元素Class',
  `element_text` VARCHAR(500) NULL COMMENT '元素文本',

  `extra_data` TEXT NULL COMMENT '扩展数据(JSON格式)',

  `session_id` VARCHAR(100) NULL COMMENT '会话ID',
  `ip_address` VARCHAR(50) NULL COMMENT 'IP地址',
  `user_agent` TEXT NULL COMMENT '用户代理',
  `browser` VARCHAR(50) NULL COMMENT '浏览器',
  `os` VARCHAR(50) NULL COMMENT '操作系统',
  `device_type` VARCHAR(50) NULL COMMENT '设备类型: desktop(桌面), mobile(手机), tablet(平板)',

  `stay_time` BIGINT NULL COMMENT '页面停留时间(毫秒)',
  `load_time` BIGINT NULL COMMENT '页面加载时间(毫秒)',

  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',

  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_event_type` (`event_type`),
  INDEX `idx_create_time` (`create_time`),
  INDEX `idx_page_url` (`page_url`(191)),
  INDEX `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='埋点日志表';
