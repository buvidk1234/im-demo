package com.it.imdemo.infrastructure.repository.entity;


import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FriendshipEntity {
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
}
