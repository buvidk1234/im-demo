package com.it.imdemo.infrastructure.persistence.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("group_members")
public class GroupMemberEntity {
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
}
