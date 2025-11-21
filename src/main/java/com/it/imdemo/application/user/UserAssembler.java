package com.it.imdemo.application.user;


public class UserAssembler {
    public static UserLoginResp toUserLoginResponse(com.it.imdemo.domain.user.model.User user) {
        return UserLoginResp.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatarUrl(user.getAvatarUrl())
                .phone(user.getContactInformation() != null ? user.getContactInformation().getPhone() : null)
                .email(user.getContactInformation() != null ? user.getContactInformation().getEmail() : null)
                .build();
    }
}
