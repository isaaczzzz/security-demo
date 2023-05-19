package com.nozuch.securitydemo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nozuch.securitydemo.common.domain.LoginUser;
import com.nozuch.securitydemo.dao.entity.SysPerm;
import com.nozuch.securitydemo.dao.entity.SysUser;
import com.nozuch.securitydemo.dao.repository.SysPermRepository;
import com.nozuch.securitydemo.dao.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现UserDetailsService接口，用于加载用户信息，鉴权过程中会自动调用
 */
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysPermRepository sysPermRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询用户信息
        SysUser sysUser = sysUserRepository.getBaseMapper().selectOne(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, username)
        );
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 查询对应权限信息
        List<SysPerm> sysPerms = sysPermRepository.selectPermByUserId(sysUser.getId());
        // 将sysPerms中的perm字符串添加到列表
        List<String> permissions = sysPerms.stream()
                .map(SysPerm::getPerm)
                .collect(Collectors.toList());
        return new LoginUser(sysUser, permissions);
    }
}
