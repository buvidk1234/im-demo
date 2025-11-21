package com.it.imdemo.interfaces.http;

import com.it.imdemo.infrastructure.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService; // 下面定义的工具类

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    // Java 17+ Record 写法 (DTO)
    public record LoginReq(String username, String password) {}
    public record LoginResp(String token, long expiresIn) {}

    @PostMapping("/login")
    public LoginResp login(@RequestBody LoginReq req) {
        // 1. 【核心】执行认证
        // 如果密码错，这里会直接抛出 BadCredentialsException，被全局异常处理器捕获
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        // 2. 生成 Token
        String token = tokenService.generateToken(authentication);

        return new LoginResp(token, 3600L);
    }
}
