package com.it.imdemo.application.user;

import com.it.imdemo.application.assembler.UserAssembler;
import com.it.imdemo.domain.user.UserRepository;
import com.it.imdemo.domain.user.model.User;
import com.it.imdemo.shared.JwtUtil;
import com.it.imdemo.shared.exception.AuthenticationException;
import com.it.imdemo.shared.exception.BizErrorCode;
import com.it.imdemo.shared.exception.BizException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class UserApplicationService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private JwtUtil jwtUtil;

    public void register(UserRegisterCmd cmd) {
        User user = User.create(cmd.getUsername(), cmd.getPassword(), cmd.getNickname(),cmd.getAvatarUrl(), cmd.getPhone(), cmd.getEmail());
        userRepository.save(user);
    }

    public UserLoginResponse login(UserLoginQry qry) {
        User user = userRepository.getByUsername(qry.getUsername())
                .orElseThrow(() -> new BizException(BizErrorCode.GROUP_NOT_FOUND));
        if(!user.validateLogin(qry.getPassword())){
            throw new AuthenticationException("Invalid credentials");
        }
        UserLoginResponse userLoginResponse = UserAssembler.toUserLoginResponse(user);
        String token = jwtUtil.generateToken(user.getId().toString());
        userLoginResponse.setToken(token);
        return userLoginResponse;
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    public void disableUser(Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.disable();
        userRepository.save(user);
    }



    public void assertAvailable(Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.assertAvailable();
    }

    public void makeUserOnline(Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.makeOnline();
        userRepository.save(user);
    }
    public void makeUserOffline(Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.makeOffline();
        userRepository.save(user);
    }
}
