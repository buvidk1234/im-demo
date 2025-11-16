package com.it.imdemo.domain.group.model;


import com.it.imdemo.application.group.CreateGroupCmd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroup {


    private Long id;

    private String name;

    private String description;

    private Long ownerId;

    private String avatarUrl;

    private Integer memberCount;
    // why not final, if you are a vip, you can have more members
    private Integer maxMemberCount;

    private Date createdAt;

    private Date updatedAt;


    public static ChatGroup create(String name,String description, Long owerId, String avatarUrl) {
        ChatGroup chatGroup = ChatGroup.builder().name(name).description(description).ownerId(owerId).avatarUrl(avatarUrl).build();
        if(chatGroup.name == null || chatGroup.name.isEmpty()) {
            chatGroup.name = "New Group";
        }
        if(chatGroup.avatarUrl == null || chatGroup.avatarUrl.isEmpty()) {
            chatGroup.avatarUrl = "https://example.com/default-avatar.png";
        }
        chatGroup.memberCount  = 0;
        chatGroup.maxMemberCount=200;
        chatGroup.createdAt = new Date();
        chatGroup.updatedAt = new Date();
        return chatGroup;
    }

    public void addMember(Long userToAddId) {
        memberCount++;
    }

    public void assertNotFull() {
        if(memberCount >= maxMemberCount) {
            throw new IllegalArgumentException("群成员已达上限");
        }
    }
}
