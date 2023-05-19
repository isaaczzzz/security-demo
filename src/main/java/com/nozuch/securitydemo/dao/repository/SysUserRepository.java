package com.nozuch.securitydemo.dao.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nozuch.securitydemo.dao.entity.SysUser;
import com.nozuch.securitydemo.dao.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserRepository extends ServiceImpl<SysUserMapper, SysUser> {

}
