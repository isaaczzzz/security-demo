package com.nozuch.securitydemo.dao.repository;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nozuch.securitydemo.dao.entity.SysPerm;
import com.nozuch.securitydemo.dao.entity.SysRolePerm;
import com.nozuch.securitydemo.dao.entity.SysUserRole;
import com.nozuch.securitydemo.dao.mapper.SysPermMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SysPermRepository extends MPJBaseServiceImpl<SysPermMapper, SysPerm> {
    @Resource
    private SysPermMapper sysPermMapper;

    // 联表查询，根据用户id查询权限信息
    public List<SysPerm> selectPermByUserId(Long userId) {
        MPJLambdaWrapper<SysPerm> wrapper = new MPJLambdaWrapper<SysPerm>()
                .selectAll(SysPerm.class)
                .leftJoin(SysRolePerm.class, SysRolePerm::getPermId, SysPerm::getPermId)
                .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRolePerm::getRoleId)
                .eq(SysUserRole::getUserId, userId);
        return sysPermMapper.selectJoinList(SysPerm.class, wrapper);
    }
}
