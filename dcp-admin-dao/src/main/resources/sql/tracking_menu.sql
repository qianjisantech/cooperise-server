-- 埋点管理菜单SQL
-- 注意：需要先查询操作日志的父菜单ID，然后替换下面的 @parent_id

-- 方式1：如果操作日志是一级菜单，需要先获取其ID
-- SELECT id FROM sys_menu WHERE menu_name = '操作日志' AND parent_id = 0;

-- 方式2：如果操作日志和埋点管理都在同一个父菜单下（如：系统管理），需要先获取父菜单ID
-- SELECT id FROM sys_menu WHERE menu_name = '系统管理' AND parent_id = 0;

-- 插入埋点管理菜单（假设和操作日志同级，都在根目录下）
INSERT INTO `sys_menu` (
  `parent_id`,
  `menu_name`,
  `menu_type`,
  `path`,
  `component`,
  `icon`,
  `perms`,
  `order_num`,
  `visible`,
  `status`,
  `create_time`,
  `update_time`
) VALUES (
  0,                                    -- parent_id: 0表示一级菜单，如果要放在其他菜单下，需要修改为对应菜单的ID
  '埋点管理',                            -- menu_name
  'C',                                  -- menu_type: C-菜单 M-目录 F-按钮
  '/tracking',                          -- path
  'tracking/TrackingLog',               -- component
  'chart-line',                         -- icon
  NULL,                                 -- perms: 菜单权限标识，NULL表示不需要权限
  100,                                  -- order_num: 排序号，建议设置为操作日志的order_num + 1
  1,                                    -- visible: 1-显示 0-隐藏
  1,                                    -- status: 1-正常 0-停用
  NOW(),                                -- create_time
  NOW()                                 -- update_time
);

-- 获取刚插入的菜单ID
SET @menu_id = LAST_INSERT_ID();

-- 插入埋点管理的按钮权限
INSERT INTO `sys_menu` (
  `parent_id`,
  `menu_name`,
  `menu_type`,
  `path`,
  `component`,
  `perms`,
  `order_num`,
  `visible`,
  `status`,
  `create_time`,
  `update_time`
) VALUES
(
  @menu_id,
  '埋点查询',
  'F',
  NULL,
  NULL,
  'tracking:view',
  1,
  1,
  1,
  NOW(),
  NOW()
);

-- 可选：如果需要赋予admin角色访问权限
-- 首先查询admin角色ID和埋点管理菜单ID
-- SELECT id FROM sys_role WHERE role_key = 'admin';
-- 然后插入角色菜单关系
-- INSERT INTO sys_role_menu (role_id, menu_id)
-- SELECT r.id, @menu_id FROM sys_role r WHERE r.role_key = 'admin';
