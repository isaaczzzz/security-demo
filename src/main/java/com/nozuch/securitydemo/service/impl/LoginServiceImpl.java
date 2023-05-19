package com.nozuch.securitydemo.service.impl;

import com.nozuch.securitydemo.common.constant.SecurityConst;
import com.nozuch.securitydemo.common.domain.LoginUser;
import com.nozuch.securitydemo.common.util.RedisCache;
import com.nozuch.securitydemo.common.util.TokenGenerator;
import com.nozuch.securitydemo.service.LoginService;
import com.nozuch.securitydemo.vo.reqVO.LoginReqVO;
import com.nozuch.securitydemo.vo.respVO.LoginRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    @Override
    public LoginRespVO login(LoginReqVO reqVO) {
        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqVO.getUsername(), reqVO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        // 认证成功
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getSysUser().getId();
        // 生成Token并储存用户信息到redis
        /**
         * 无需使用jwt，生成token后与用户信息一并存入redis
         * 使用token验证时将redis中用户信息用于存入SecurityContextHolder
         */
        String token = TokenGenerator.generate(loginUser.getSysUser().getSalt(), loginUser.getSysUser().getId().toString());
        loginUser.setToken(token);
        redisCache.setCacheObject(SecurityConst.TOKEN_PREFIX + id, loginUser, SecurityConst.TOKEN_TIMEOUT, TimeUnit.SECONDS);
        return LoginRespVO.builder()
                .id(id)
                .token(id+"_"+token)
                .build();
    }

    @Override
    public Boolean logout() {
        // 获取SecurityContextHolder中的用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = loginUser.getSysUser().getId();
        // 删除redis中信息
        return redisCache.deleteObject(SecurityConst.TOKEN_PREFIX + id);
    }
}
