package com.it.imdemo.domain.group;

import com.it.imdemo.domain.group.model.ChatGroup;
import com.it.imdemo.domain.group.model.GroupMember;
import com.it.imdemo.domain.user.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GroupDomainService {

    @Resource
    private GroupMemberRepository groupMemberRepository;

    public GroupMember addMemberToGroup(ChatGroup chatGroup, User operator, User userToAdd) {

        if(!operator.isActive()){
            throw new RuntimeException("Operator is not active");
        }
        if(!userToAdd.isActive()){
            throw new RuntimeException("User to add is not active");
        }
        if(operator.getId().equals(userToAdd.getId())){
            throw new IllegalArgumentException("不能添加自己到群组");
        }

        boolean isExist = groupMemberRepository.exists(chatGroup.getId(), userToAdd.getId());
        if (isExist) {
            throw new IllegalArgumentException("成员已存在群组中");
        }
        return GroupMember.builder()
                .groupId(chatGroup.getId())
                .userId(userToAdd.getId())
                .role(GroupMember.Role.MEMBER.getValue())
                .build();
    }

}
