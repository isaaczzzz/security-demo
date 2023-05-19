package com.nozuch.securitydemo.controller;

import com.nozuch.securitydemo.common.domain.Result;
import com.nozuch.securitydemo.service.LoginService;
import com.nozuch.securitydemo.vo.reqVO.LoginReqVO;
import com.nozuch.securitydemo.vo.respVO.LoginRespVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<LoginRespVO> login(@RequestBody LoginReqVO loginReqVO) {
        return Result.success(loginService.login(loginReqVO));
    }

    @GetMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(loginService.logout());
    }
}
