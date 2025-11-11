package com.it.imdemo.domain.friendship.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class Friendship {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private Long userId;
    @Schema(description="")
    private Long friendId;
    @Schema(description="0=pending,1=accepted,2=blocked,3=deleted")
    private Integer status;
    @Schema(description="")
    private Date createdAt;
    @Schema(description="")
    private Date updatedAt;

    public void addFriendship(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
        this.status = 0; // pending
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public void agreeFriendship() {
        this.status = 1; // accepted
        this.updatedAt = new Date();
    }
}
