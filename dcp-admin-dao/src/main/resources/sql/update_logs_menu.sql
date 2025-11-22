-- 更新菜单：将操作日志和埋点管理合并为记录管理
USE dcp;

-- 1. 查看当前菜单状态
SELECT id, parent_id, menu_name, menu_code, path, component
FROM sys_menu
WHERE menu_code IN ('operation_log', 'tracking') OR menu_name IN ('操作日志', '埋点管理');

-- 2. 更新操作日志菜单为记录管理
UPDATE sys_menu
SET
  menu_name = '记录',
  menu_code = 'logs',
  path = '/logs',
  component = 'logs/LogManagement',
  icon = 'file-text',
  update_time = NOW()
WHERE menu_code = 'operation_log' OR menu_name = '操作日志';

-- 3. 删除埋点管理菜单（如果存在）
DELETE FROM sys_menu WHERE menu_code = 'tracking' OR menu_name = '埋点管理';

-- 4. 删除埋点查询按钮权限（如果存在）
DELETE FROM sys_menu WHERE menu_code = 'tracking:view';

-- 5. 验证更新结果
SELECT id, parent_id, menu_name, menu_code, path, component
FROM sys_menu
WHERE menu_code = 'logs';

-- 6. 清理角色菜单关联（可选，如果之前有分配埋点管理权限）
-- DELETE FROM sys_role_menu WHERE menu_id IN (SELECT id FROM sys_menu WHERE menu_code LIKE 'tracking%');
