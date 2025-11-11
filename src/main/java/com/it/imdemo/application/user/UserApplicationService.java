package com.it.imdemo.application.user;

import com.it.imdemo.domain.user.UserRepository;
import com.it.imdemo.domain.user.model.User;
import com.it.imdemo.shared.JwtUtil;
import com.it.imdemo.shared.exception.AuthenticationException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class UserApplicationService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private JwtUtil jwtUtil;

    public void register(UserRegisterCmd cmd) {
        User user = new User();
        BeanUtils.copyProperties(cmd, user);
        user.initializeForRegister();
        userRepository.save(user);
    }

    public UserLoginResponse login(UserLoginQry qry) {
        User user = userRepository.getByUsername(qry.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));
        if(!user.validateLogin(qry.getPassword())){
            throw new AuthenticationException("Invalid credentials");
        }
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        BeanUtils.copyProperties(user, userLoginResponse);
        String token = jwtUtil.generateToken(user.getId().toString());
        userLoginResponse.setToken(token);
        return userLoginResponse;
    }
}
