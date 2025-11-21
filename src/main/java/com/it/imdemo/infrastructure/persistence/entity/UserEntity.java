package com.it.imdemo.infrastructure.persistence.entity;


import java.util.Date;

import com.it.imdemo.domain.user.model.OnlineStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserEntity {
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

    // 0=OFFLINE,1=ONLINE,2=AWAY
    private Integer onlineStatus;
    private Integer preferredOnlineStatus;
    private Date lastOnlineAt;
}
