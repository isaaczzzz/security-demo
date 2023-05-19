package com.nozuch.securitydemo;

import com.nozuch.securitydemo.dao.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MapperTest {
    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    void testSysUserMapper() {
        System.out.println(sysUserMapper.selectById(null));
    }
}
