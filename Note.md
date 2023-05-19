# 核心流程
### 用户名密码登录
具体在LoginServiceImpl中实现Spring Security的LoginService方法，
并在完成登陆后生成token并存储到redis。

在调用AuthenticationManager时需实现UserDetailService，
在其中实现从数据库查询用户信息，并完成权限信息封装。

### token认证
通过AuthenticationTokenFilter实现拦截，从请求头中获取token，与redis中缓存token进行比对，
若一致则认证通过，否则认证失败。

### 异常处理
在AccessDeniedHandlerImpl中实现认证的异常信息返回（包括登录和token）。

在AuthenticationEntryPointImpl中实现鉴权异常信息返回。

### Spring Security配置
详见SecurityConfig类。