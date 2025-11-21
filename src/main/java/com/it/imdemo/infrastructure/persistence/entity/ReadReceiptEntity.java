package com.it.imdemo.infrastructure.persistence.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("read_receipts")
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
