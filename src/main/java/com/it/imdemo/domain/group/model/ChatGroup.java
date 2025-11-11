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

    @Schema(description="")
    private Long id;
    @Schema(description="")
    private String name;
    @Schema(description="")
    private String description;
    @Schema(description="")
    private Long ownerId;
    @Schema(description="")
    private String avatarUrl;
    @Schema(description="")
    private Date createdAt;
    @Schema(description="")
    private Date updatedAt;


    public static ChatGroup create(String name,String description, Long owerId, String avatarUrl) {
        ChatGroup chatGroup = ChatGroup.builder().name(name).description(description).ownerId(owerId).avatarUrl(avatarUrl).build();
        if(chatGroup.name == null || chatGroup.name.isEmpty()) {
            chatGroup.name = "New Group";
        }
        if(chatGroup.avatarUrl == null || chatGroup.avatarUrl.isEmpty()) {
            chatGroup.avatarUrl = "https://example.com/default-avatar.png";
        }
        chatGroup.createdAt = new Date();
        chatGroup.updatedAt = new Date();
        return chatGroup;
    }
}
