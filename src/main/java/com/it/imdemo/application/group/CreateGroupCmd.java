package com.it.imdemo.application.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateGroupCmd {
    @Schema(description="")
    private String name;
    @Schema(description="")
    private String description;
    @Schema(description="")
    private Long ownerId;
    @Schema(description="")
    private String avatarUrl;
}
