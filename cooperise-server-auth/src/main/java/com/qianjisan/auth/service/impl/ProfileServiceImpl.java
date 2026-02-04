package com.qianjisan.auth.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.qianjisan.auth.service.IAuthService;
import com.qianjisan.auth.service.ProfileService;
import com.qianjisan.auth.vo.LoginResponseVO;
import com.qianjisan.auth.vo.UserProfileVO;
import com.qianjisan.common.service.IAsyncEmailService;
import com.qianjisan.common.service.IVerificationCodeService;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.core.utils.BeanConverter;
import com.qianjisan.core.utils.JwtUtil;
import com.qianjisan.core.utils.UserCodeGenerator;
import com.qianjisan.enterprise.dto.UserMyEnterpriseDTO;
import com.qianjisan.enterprise.mapper.UserEnterpriseMapper;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.service.ISysMenuService;
import com.qianjisan.system.service.ISysUserService;
import com.qianjisan.system.vo.SysMenuTreeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ISysUserService userService;
    private final ISysMenuService menuService;
    private final UserEnterpriseMapper userEnterpriseMapper;




    @Override
    public UserProfileVO getUserProfile(Long userId) {
        log.info("[ProfileService] 获取用户权限信息: {}", userId);

        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 获取用户完整信息
        SysUser sysUser = userService.getById(userId);
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }

        UserProfileVO profile = new UserProfileVO();

        // 用户基本信息
        UserProfileVO.UserInfoVo userInfoVO = BeanConverter.convert(sysUser, UserProfileVO.UserInfoVo::new);
        profile.setUserInfo(userInfoVO);

        // 判断是否为 admin 用户
        boolean isAdmin = "admin".equalsIgnoreCase(sysUser.getName());

        // 获取用户菜单权限
        List<SysMenuTreeVO> menuTrees = menuService.getUserMenuTree(userId);
        List<String> menuPermissions = menuService.getUserMenuPermissions(userId);


        // 检查用户是否有角色（通过菜单权限判断）
        boolean hasRole = menuTrees != null && !menuTrees.isEmpty();

        if (!hasRole && !isAdmin) {
            // 没有角色的用户：返回空的权限和菜单
            log.warn("[AuthService] 用户没有分配角色，用户ID: {}", userId);
            profile.setMenus(new ArrayList<>());
            profile.setMenuPermissions(new String[0]);
            profile.setRoles(new String[0]);


            log.info("[AuthService] 返回空权限信息（无角色用户）");
            return profile;
        }

        if (CollectionUtil.isEmpty(menuTrees)) {
            profile.setMenus(List.of());
        } else {
            List<UserProfileVO.UserMenuVo> userMenuVos = menuTrees.stream().map(menuTree -> {
                UserProfileVO.UserMenuVo userMenuVo = new UserProfileVO.UserMenuVo();
                userMenuVo.setId(menuTree.getId());
                userMenuVo.setMenuName(menuTree.getMenuName());
                userMenuVo.setMenuCode(menuTree.getMenuCode());
                userMenuVo.setMenuType(menuTree.getMenuType());
                userMenuVo.setComponent(menuTree.getComponent());
                userMenuVo.setSortOrder(menuTree.getSortOrder());
                userMenuVo.setPermission(menuTree.getPermission());
                userMenuVo.setIcon(menuTree.getIcon());
                userMenuVo.setPath(menuTree.getPath());
                userMenuVo.setVisible(menuTree.getVisible());
                userMenuVo.setComponent(menuTree.getComponent());
                userMenuVo.setParentId(menuTree.getParentId());
                userMenuVo.setChildren(menuTree.getChildren());
                return userMenuVo;
            }).collect(Collectors.toList());
            profile.setMenus(userMenuVos);

        }
        // 设置菜单相关信息

        profile.setMenuPermissions(menuPermissions.toArray(new String[0]));

        if (isAdmin) {
            // admin 用户拥有所有菜单权限（如果菜单为空，添加通配符）
            if (menuPermissions.isEmpty()) {
                profile.setMenuPermissions(new String[]{"*:*:*"});
            }
            profile.setRoles(new String[]{"admin"});

        } else {
            // 普通用户角色
            profile.setRoles(new String[]{"user"});

        }


        try {
            List<UserMyEnterpriseDTO> selfUserCompanyDTOS = userEnterpriseMapper.selectEnterprisesByUserId(userId);
            if (selfUserCompanyDTOS != null && !selfUserCompanyDTOS.isEmpty()) {

                List<UserProfileVO.UserEnterpriseVo> companyVos = new ArrayList<>();

                for (UserMyEnterpriseDTO c : selfUserCompanyDTOS) {
                    UserProfileVO.UserEnterpriseVo cv = new UserProfileVO.UserEnterpriseVo();
                    cv.setId(c.getId());
                    cv.setCode(c.getCode());
                    cv.setName(c.getName());
                    cv.setIsDefault(c.getIsDefault() == 1);
                    companyVos.add(cv);
                }
                log.info("[AuthService] getUserProfile 查询用户企业成功，用户ID: {}, 企业列表为 {}", userId, companyVos);
                profile.setEnterprises(companyVos);
            } else {
                profile.setEnterprises(new ArrayList<>());

            }
        } catch (Exception e) {
            log.error("[AuthService] getUserProfile 查询用户企业失败，用户ID: {}, 错误: {}", userId, e.getMessage());
            profile.setEnterprises(new ArrayList<>());

        }

        return profile;
    }
}
