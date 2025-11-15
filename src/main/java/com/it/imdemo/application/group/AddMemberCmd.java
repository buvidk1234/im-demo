package com.it.imdemo.application.group;

import lombok.Data;

@Data
public class AddMemberCmd {
    private Long groupId;
    private Long operatorId;
    private Long userToAddId;
}
