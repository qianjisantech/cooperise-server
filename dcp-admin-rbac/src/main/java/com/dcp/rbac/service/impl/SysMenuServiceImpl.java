package com.dcp.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.rbac.request.SysMenuRequest;
import com.dcp.rbac.vo.SysMenuVO;
import com.dcp.rbac.entity.SysMenu;
import com.dcp.rbac.mapper.SysMenuMapper;
import com.dcp.rbac.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysMenuMapper menuMapper;

    @Override
    public void saveMenu(SysMenuRequest request) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(request, menu);
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenuById(Long id, SysMenuRequest request) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        BeanUtils.copyProperties(request, menu);
        menuMapper.updateById(menu);
    }

    @Override
    public List<SysMenuVO> getMenuTree() {
        // 查询所有菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSortOrder);
        List<SysMenu> allMenus = menuMapper.selectList(wrapper);

        // 转换为VO
        List<SysMenuVO> allMenuVOs = allMenus.stream().map(menu -> {
            SysMenuVO vo = new SysMenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 构建树形结构
        return buildMenuTree(allMenuVOs, 0L);
    }

    @Override
    public List<SysMenuVO> getMenuList() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSortOrder);
        List<SysMenu> menus = menuMapper.selectList(wrapper);

        return menus.stream().map(menu -> {
            SysMenuVO vo = new SysMenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public SysMenuVO getMenuDetail(Long id) {
        SysMenu menu = menuMapper.selectById(id);
        if (menu == null) {
            return null;
        }

        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(menu, vo);
        return vo;
    }

    @Override
    public List<SysMenuVO> getUserMenuTree(Long userId) {
        List<SysMenu> userMenus;

        // 查询用户的菜单（通过角色关联）
        userMenus = menuMapper.selectMenusByUserId(userId);

        if (userMenus == null || userMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 转换为VO
        List<SysMenuVO> menuVOs = userMenus.stream().map(menu -> {
            SysMenuVO vo = new SysMenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 构建树形结构
        return buildMenuTree(menuVOs, 0L);
    }

    @Override
    public List<String> getUserMenuPermissions(Long userId) {
        List<SysMenu> userMenus;

        // 查询用户的菜单
        userMenus = menuMapper.selectMenusByUserId(userId);

        if (userMenus == null || userMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 提取权限标识（过滤掉null和空字符串）
        return userMenus.stream()
                .map(SysMenu::getPermission)
                .filter(permission -> permission != null && !permission.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 递归构建菜单树
     *
     * @param allMenus 所有菜单
     * @param parentId 父菜单ID
     * @return 菜单树
     */
    private List<SysMenuVO> buildMenuTree(List<SysMenuVO> allMenus, Long parentId) {
        List<SysMenuVO> result = new ArrayList<>();

        for (SysMenuVO menu : allMenus) {
            if (menu.getParentId().equals(parentId)) {
                // 递归查找子菜单
                List<SysMenuVO> children = buildMenuTree(allMenus, menu.getId());
                if (!children.isEmpty()) {
                    menu.setChildren(children);
                }
                result.add(menu);
            }
        }

        return result;
    }
}
