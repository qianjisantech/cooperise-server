-- ============================================
-- 日志管理菜单插入脚本
-- 包含操作记录和埋点记录的融合组件
-- ============================================

USE dcp;

-- 插入一级菜单：日志管理
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
  0,                          -- 一级菜单
  '日志管理',                 -- 菜单名称
  'logs',                     -- 菜单编码
  2,                          -- 类型：2=菜单
  '/logs',                    -- 路由路径
  'logs/LogManagement',       -- 组件路径（对应 src/views/logs/LogManagement.vue）
  'file-text',                -- 图标
  60,                         -- 排序号（可根据实际情况调整）
  1,                          -- 可见
  1,                          -- 启用
  'logs:view',                -- 权限标识
  NOW(),
  NOW(),
  0
);

-- 获取刚插入的菜单ID
SET @logs_menu_id = LAST_INSERT_ID();

-- 插入按钮权限：查看操作记录
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
) VALUES
(
  @logs_menu_id,
  '查看操作记录',
  'operation:view',
  3,                          -- 类型：3=按钮
  NULL,
  NULL,
  NULL,
  1,
  1,
  1,
  'logs:operation:view',
  NOW(),
  NOW(),
  0
),
(
  @logs_menu_id,
  '查看操作详情',
  'operation:detail',
  3,
  NULL,
  NULL,
  NULL,
  2,
  1,
  1,
  'logs:operation:detail',
  NOW(),
  NOW(),
  0
),
(
  @logs_menu_id,
  '查看埋点记录',
  'tracking:view',
  3,
  NULL,
  NULL,
  NULL,
  3,
  1,
  1,
  'logs:tracking:view',
  NOW(),
  NOW(),
  0
),
(
  @logs_menu_id,
  '查看埋点详情',
  'tracking:detail',
  3,
  NULL,
  NULL,
  NULL,
  4,
  1,
  1,
  'logs:tracking:detail',
  NOW(),
  NOW(),
  0
);

-- 查看插入结果
SELECT
  id,
  parent_id,
  menu_name,
  menu_type,
  path,
  component,
  permission,
  CASE menu_type
    WHEN 1 THEN '目录'
    WHEN 2 THEN '菜单'
    WHEN 3 THEN '按钮'
  END AS type_label
FROM sys_menu
WHERE menu_code IN ('logs', 'operation:view', 'operation:detail', 'tracking:view', 'tracking:detail')
ORDER BY parent_id, sort_order;

-- ============================================
-- 为管理员角色分配权限（可选）
-- ============================================

-- 假设管理员角色ID为1，将日志管理菜单分配给管理员
-- 请根据实际的角色ID调整
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE menu_code = 'logs';

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE menu_code IN ('operation:view', 'operation:detail', 'tracking:view', 'tracking:detail');

-- 查看角色菜单关系
SELECT
  srm.role_id,
  sr.role_name,
  sm.menu_name,
  sm.permission
FROM sys_role_menu srm
JOIN sys_role sr ON srm.role_id = sr.id
JOIN sys_menu sm ON srm.menu_id = sm.id
WHERE sm.menu_code IN ('logs', 'operation:view', 'operation:detail', 'tracking:view', 'tracking:detail')
ORDER BY srm.role_id, sm.sort_order;

-- ============================================
-- 说明
-- ============================================

/*
菜单结构：
├── 日志管理 (/logs)
    ├── 操作记录 Tab (权限: logs:operation:view)
    │   └── 查看详情 (权限: logs:operation:detail)
    └── 埋点记录 Tab (权限: logs:tracking:view)
        └── 查看详情 (权限: logs:tracking:detail)

组件位置：
- 主组件: src/views/logs/LogManagement.vue
- 操作记录Tab: src/views/logs/components/OperationLogTab.vue
- 埋点记录Tab: src/views/logs/components/TrackingLogTab.vue

使用说明：
1. 执行此SQL脚本后，菜单数据将被插入到数据库
2. 需要将菜单权限分配给相应的角色
3. 用户登录后，如果角色有相应权限，则可以在左侧菜单看到"日志管理"
4. 点击菜单后，页面会显示两个Tab：操作记录 和 埋点记录
*/
