package com.it.imdemo.domain.user.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private String username;
    @Schema(description="")
    private String password;
    @Schema(description="")
    private String nickname;
    @Schema(description="")
    private String avatarUrl;
    @Schema(description="")
    private String phone;
    @Schema(description="")
    private String email;
    @Schema(description="1=active,0=disabled")
    private Integer status;
    @Schema(description="")
    private Date createdAt;
    @Schema(description="")
    private Date updatedAt;

    public void initializeForRegister() {
        this.status = 1;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }


    public boolean validateLogin(String password) {
        return status.equals(1) && this.password.equals(password);
    }

    public boolean isActive() {
        return status.equals(1);
    }
}
