package com.it.imdemo.application.message;

import com.it.imdemo.application.group.GroupApplicationService;
import com.it.imdemo.domain.message.ConversationRepository;
import com.it.imdemo.domain.message.event.GroupMessageSentEvent;
import com.it.imdemo.domain.message.ReadReceiptRepository;
import com.it.imdemo.domain.message.model.ReadReceipt;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GroupMessageSentEventListener {

    @Resource
    private ReadReceiptRepository readReceiptRepository;
    @Resource
    private GroupApplicationService groupApplicationService;
    @Resource
    private ConversationRepository conversationRepository;

    @EventListener
    public void onGroupMessageSent(GroupMessageSentEvent event) {

        groupApplicationService.findMembersOfGroup(event.getTargetId())
                .stream()
                .filter(userId -> !userId.equals(event.getSenderId()))
                .forEach(userId -> {
                    ReadReceipt receipt = new ReadReceipt();
                    receipt.setMessageId(event.getMessageId());
                    receipt.setUserId(userId);
                    receipt.setReadAt(null); // 初始未读
                    readReceiptRepository.save(receipt);
                });

    }
}
