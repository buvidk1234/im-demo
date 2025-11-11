package com.it.imdemo.infrastructure.repository.entity;


import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReadReceiptEntity {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private Long messageId;
    @Schema(description="")
    private Long userId;
    @Schema(description="")
    private Date readAt;
}
