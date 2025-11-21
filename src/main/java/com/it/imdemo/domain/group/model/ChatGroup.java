package com.it.imdemo.domain.group.model;


import com.it.imdemo.commons.exception.BizErrorCode;
import com.it.imdemo.commons.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            throw new BizException(BizErrorCode.GROUP_MEMBER_LIMIT_REACHED);
        }
    }
}
