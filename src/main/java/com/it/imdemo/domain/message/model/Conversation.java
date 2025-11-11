package com.it.imdemo.domain.message.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class Conversation {
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

    public static Conversation create(Integer type, Long senderId, Long receiverId) {
        Conversation conversation = new Conversation();
        conversation.setType(type);
        conversation.setUserAId(senderId);
        if(type == 0) {
            conversation.setUserBId(receiverId);
        }else if(type == 1) {
            conversation.setGroupId(receiverId);
        }
//        conversation.setLastMessageId(lastMessageId);
        conversation.setCreatedAt(new Date());
        conversation.setUpdatedAt(new Date());
        return conversation;
    }
}
