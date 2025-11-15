package com.it.imdemo.application.message;

import com.it.imdemo.domain.group.GroupMemberRepository;
import com.it.imdemo.domain.message.ConversationRepository;
import com.it.imdemo.domain.message.MessageRepository;
import com.it.imdemo.domain.message.ReadReceiptRepository;
import com.it.imdemo.domain.message.model.Conversation;
import com.it.imdemo.domain.message.model.Message;
import com.it.imdemo.domain.message.model.ReadReceipt;
import com.it.imdemo.domain.message.WebSocketGateway;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageApplicationService {

    @Resource
    private MessageRepository messageRepository;
    @Resource
    private ConversationRepository conversationRepository;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @Resource
    private ReadReceiptRepository readReceiptRepository;

    @Resource
    private ApplicationEventPublisher publisher;


    @Transactional
    public void sendMessage(SendMessageCmd cmd) {

        Conversation conversation = conversationRepository.findByTypeAndSenderIdAndReceiverId(cmd.getType(), cmd.getSenderId(), cmd.getTargetId())
                .orElseGet(() -> {
                    Conversation c = Conversation.create(cmd.getType(), cmd.getSenderId(), cmd.getTargetId());
                    conversationRepository.save(c);
                    return c;
                });

        Message message = Message.create(
                conversation.getId(),
                cmd.getSenderId(),
                cmd.getTargetId(),
                cmd.getMessageType(),
                cmd.getContent());
        messageRepository.save(message);
        conversation.setLastMessageId(message.getId());
        conversationRepository.save(conversation);

        publisher.publishEvent(new GroupMessageSentEvent(this, cmd.getSenderId(), cmd.getTargetId(), message.getId()));
    }

    @Resource
    private WebSocketGateway webSocketGateway;

    public void onUserOnline(Long userId) {

        List<Message> unreadMessages = messageRepository.findUnreadMessagesByUserId(userId);

        // push unread messages
        webSocketGateway.pushUnreadMessagesToUser(userId, unreadMessages);
    }

}
