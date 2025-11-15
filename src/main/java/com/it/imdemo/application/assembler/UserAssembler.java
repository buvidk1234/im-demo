package com.it.imdemo.application.assembler;

import com.it.imdemo.application.user.UserLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserAssembler {
    public static UserLoginResponse toUserLoginResponse(com.it.imdemo.domain.user.model.User user) {
        return UserLoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatarUrl(user.getAvatarUrl())
                .phone(user.getContactInformation() != null ? user.getContactInformation().getPhone() : null)
                .email(user.getContactInformation() != null ? user.getContactInformation().getEmail() : null)
                .build();
    }
}
