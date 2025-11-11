package com.it.imdemo.infrastructure.repository.entity;


import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConversationEntity {
    @Schema(description="")
    private Long id;
    @Schema(description="0=single,1=group")
    private Integer type;
    @Schema(description="单聊参与者A")
    private Long userAId;
    @Schema(description="单聊参与者B")
    private Long userBId;
    @Schema(description="群聊ID")
    private Long groupId;
    @Schema(description="最近一条消息ID(无外键避免循环)")
    private Long lastMessageId;
    @Schema(description="")
    private Date updatedAt;
    @Schema(description="")
    private Date createdAt;
}
