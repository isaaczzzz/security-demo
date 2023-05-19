package com.nozuch.securitydemo;

import com.nozuch.securitydemo.dao.repository.SysPermRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RepositoryTest {
    @Resource
    private SysPermRepository sysPermRepository;

    @Test
    void testSelectPerm() {
        sysPermRepository.selectPermByUserId(1L).forEach(System.out::println);
    }
}
