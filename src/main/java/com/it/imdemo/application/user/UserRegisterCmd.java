package com.it.imdemo.application.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRegisterCmd {


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
}
