package com.it.imdemo.infrastructure.persistence.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("messages")
public class MessageEntity {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private Long conversationId;
    @Schema(description="")
    private Long senderId;
    @Schema(description="仅单聊使用")
    private Long receiverId;
    @Schema(description="0=text,1=image,2=file,3=system")
    private Integer messageType;
    @Schema(description="")
    private String content;
    @Schema(description="")
    private Date deliveredAt;
    @Schema(description="")
    private Date readAt;
    @Schema(description="")
    private Date createdAt;
}
