package com.it.imdemo.domain.message.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class ReadReceipt {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private Long messageId;
    @Schema(description="")
    private Long userId;
    @Schema(description="")
    private Date readAt;
}
