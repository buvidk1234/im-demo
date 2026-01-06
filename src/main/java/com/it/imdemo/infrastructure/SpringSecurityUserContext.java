package com.it.imdemo.infrastructure;


import com.it.imdemo.commons.exception.BizErrorCode;
import com.it.imdemo.commons.exception.BizException;
import com.it.imdemo.domain.user.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityUserContext implements UserContext { // UserContext 是 Domain 层定义的接口

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BizException(BizErrorCode.UNAUTHENTICATED); // 业务异常
        }

        // 从 JWT 中提取 userId (假设你的 JWT claim 里叫 "uid")
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return Long.valueOf(jwt.getClaimAsString("uid"));
        }

        // 如果是简单的 UserDetails
        // return ((CustomUserDetails) authentication.getPrincipal()).getId();
        return null;
    }
}
