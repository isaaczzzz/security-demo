package com.nozuch.securitydemo.service;

import com.nozuch.securitydemo.vo.reqVO.LoginReqVO;
import com.nozuch.securitydemo.vo.respVO.LoginRespVO;

public interface LoginService {
    LoginRespVO login(LoginReqVO reqVO);

    Boolean logout();
}
