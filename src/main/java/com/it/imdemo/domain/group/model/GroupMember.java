package com.it.imdemo.domain.group.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    @Schema(description="")
    private Long id;
    @Schema(description="")
    private Long groupId;
    @Schema(description="")
    private Long userId;
    @Schema(description="0=member,1=admin,2=owner")
    private Integer role;
    @Schema(description="")
    private Date joinedAt;


    public static GroupMember create(Long groupId, Long userId, Role role) {
        return GroupMember.builder()
                .groupId(groupId)
                .userId(userId)
                .role(role.getValue())
                .joinedAt(new Date())
                .build();
    }

    @Getter
    public enum Role {
        MEMBER(0),
        ADMIN(1),
        OWNER(2);

        private final int value;

        Role(int value) {
            this.value = value;
        }

    }
}
