package com.it.imdemo.application.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long id;
    private String username;
    @Schema(description="")
    private String nickname;
    @Schema(description="")
    private String avatarUrl;
    @Schema(description="")
    private String phone;
    @Schema(description="")
    private String email;
    private String token;
}
