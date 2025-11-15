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

    public static Friendship create(Long userId, Long friendId) {
        Friendship friendship = new Friendship();
        friendship.userId = userId;
        friendship.friendId = friendId;
        friendship.status = 0; // pending
        friendship.createdAt = new Date();
        friendship.updatedAt = new Date();
        return friendship;
    }

    public void agreeFriendship() {
        this.status = 1; // accepted
        this.updatedAt = new Date();
    }


    public void removeFriendship() {
        this.status = 3; // deleted
        this.updatedAt = new Date();
    }
}
