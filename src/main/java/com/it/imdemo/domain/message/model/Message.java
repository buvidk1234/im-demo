package com.it.imdemo.domain.message.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
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

    public static Message create(Long conversationId, Long senderId, Long receiverId, Integer messageType, String content) {
        return new Message(null, conversationId, senderId, receiverId, messageType, content, new Date(), null, new Date());
    }
}
