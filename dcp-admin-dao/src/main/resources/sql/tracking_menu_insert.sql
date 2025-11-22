-- 埋点管理菜单插入SQL
-- 使用前请根据实际情况调整 parent_id 和 sort_order

USE dcp;

-- 查看操作日志的菜单信息（用于参考）
-- SELECT id, parent_id, menu_name, menu_code, menu_type, path, component, icon, sort_order
-- FROM sys_menu
-- WHERE menu_name LIKE '%操作日志%' OR menu_code LIKE '%operation%log%';

-- 插入埋点管理菜单（一级菜单）
INSERT INTO `sys_menu` (
  `parent_id`,
  `menu_name`,
  `menu_code`,
  `menu_type`,
  `path`,
  `component`,
  `icon`,
  `sort_order`,
  `visible`,
  `status`,
  `permission`,
  `create_time`,
  `update_time`,
  `is_deleted`
) VALUES (
  0,                                    -- parent_id: 0表示一级菜单
  '埋点管理',                            -- menu_name
  'tracking',                           -- menu_code
  2,                                    -- menu_type: 1-目录，2-菜单，3-按钮
  '/tracking',                          -- path
  'tracking/TrackingLog',               -- component
  'chart-line',                         -- icon
  100,                                  -- sort_order: 建议设置为操作日志的sort_order + 1
  1,                                    -- visible: 1-显示，0-隐藏
  1,                                    -- status: 1-启用，0-禁用
  NULL,                                 -- permission: 菜单权限标识
  NOW(),                                -- create_time
  NOW(),                                -- update_time
  0                                     -- is_deleted
);

-- 获取刚插入的菜单ID
SET @menu_id = LAST_INSERT_ID();

-- 插入埋点管理的查询按钮权限
INSERT INTO `sys_menu` (
  `parent_id`,
  `menu_name`,
  `menu_code`,
  `menu_type`,
  `path`,
  `component`,
  `icon`,
  `sort_order`,
  `visible`,
  `status`,
  `permission`,
  `create_time`,
  `update_time`,
  `is_deleted`
) VALUES (
  @menu_id,                             -- parent_id: 使用上面插入的菜单ID
  '埋点查询',                            -- menu_name
  'tracking:view',                      -- menu_code
  3,                                    -- menu_type: 3-按钮
  NULL,                                 -- path
  NULL,                                 -- component
  NULL,                                 -- icon
  1,                                    -- sort_order
  1,                                    -- visible
  1,                                    -- status
  'tracking:view',                      -- permission
  NOW(),                                -- create_time
  NOW(),                                -- update_time
  0                                     -- is_deleted
);

-- 验证插入结果
SELECT * FROM sys_menu WHERE menu_code = 'tracking' OR parent_id = @menu_id;

-- 可选：如果需要给admin角色授权（取消下面的注释）
-- 首先查询admin角色ID
-- SELECT id FROM sys_role WHERE role_code = 'admin' OR role_name = 'admin' OR role_name = '管理员';

-- 给admin角色分配埋点管理菜单权限（请替换 ? 为实际的角色ID）
-- INSERT INTO sys_role_menu (role_id, menu_id)
-- VALUES (?, @menu_id);

-- 给admin角色分配埋点查询按钮权限（请替换 ? 为实际的角色ID）
-- INSERT INTO sys_role_menu (role_id, menu_id)
-- VALUES (?, LAST_INSERT_ID());
