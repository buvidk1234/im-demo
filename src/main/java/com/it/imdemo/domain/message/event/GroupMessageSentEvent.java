package com.it.imdemo.domain.message.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GroupMessageSentEvent extends ApplicationEvent {

    private final long senderId;
    private final long targetId;
    private final long messageId;

    public GroupMessageSentEvent(Object source, long senderId, long targetId,long messageId) {
        super(source);
        this.senderId = senderId;
        this.targetId = targetId;
        this.messageId = messageId;
    }
}
