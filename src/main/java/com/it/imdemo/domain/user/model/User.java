package com.it.imdemo.domain.user.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private String username;
    @Schema(description="")
    private String passwordHash;
    @Schema(description="")
    private String nickname;
    @Schema(description="")
    private String avatarUrl;

    private ContactInformation contactInformation;
    @Schema(description="1=active,0=disabled")
    private Integer status;
    @Schema(description="")
    private Date createdAt;
    @Schema(description="")
    private Date updatedAt;

    private OnlineStatus onlineStatus;
    private OnlineStatus preferredOnlineStatus;
    private Date lastOnlineAt;


    public static User create(@NonNull String username,@NonNull String password, String nickname,String avatarUrl, String phone, String email) {

        return User.builder()
                .username(username)
                .passwordHash(password)
                .nickname(nickname != null ? nickname : "用户" + new Random().nextInt(10000))
                .avatarUrl(avatarUrl)
                .contactInformation(
                        ContactInformation.builder()
                                .phone(phone)
                                .email(email)
                                .build()
                )
                .status(1)
                .onlineStatus(OnlineStatus.OFFLINE)
                .preferredOnlineStatus(OnlineStatus.ONLINE)
                .lastOnlineAt(new Date())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }


    public boolean validateLogin(String password) {
        return status.equals(1) && this.passwordHash.equals(password);
    }

    public boolean isActive() {
        return status.equals(1);
    }

    public void changePassword(String newPassword) {

        this.assertPasswordNotEmpty(newPassword);

        this.assertPasswordNotSame(passwordHash, this.encryptedValue(newPassword));

        this.setPasswordHash(this.encryptedValue(newPassword));
    }

    private void assertPasswordNotSame(String password, String newPassword) {
        if(password.equals(newPassword)) {
            throw new RuntimeException("New passwordHash must be different from the current passwordHash.");
        }
    }

    private void assertPasswordNotEmpty(String newPassword) {
        if(newPassword == null || newPassword.isEmpty()) {
            throw new RuntimeException("New passwordHash must not be empty.");
        }
    }

    private String encryptedValue(String password) {
        // 盐值加密
        return password;
    }

    public void disable() {
        status = 0;
    }

    public void assertAvailable() {
        if(!isActive()) {
            throw new RuntimeException("User is disabled.");
        }
    }

    public void makeOnline() {
        this.onlineStatus = preferredOnlineStatus;
        this.lastOnlineAt = new Date();
    }

    public void makeOffline() {
        this.onlineStatus = OnlineStatus.OFFLINE;
        this.lastOnlineAt = new Date();
    }
}
