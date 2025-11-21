package com.it.imdemo.infrastructure.persistence.entity;


import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatGroupEntity {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private String name;
    @Schema(description="")
    private String description;
    @Schema(description="")
    private Long ownerId;
    @Schema(description="")
    private String avatarUrl;
    @Schema(description="")
    private Date createdAt;
    @Schema(description="")
    private Date updatedAt;

    private Integer memberCount;
    private Integer maxMemberCount;
}
