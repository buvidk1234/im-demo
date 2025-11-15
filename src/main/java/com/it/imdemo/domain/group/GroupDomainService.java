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

    public GroupMember addMemberToGroup(ChatGroup chatGroup, Long operatorId, Long userToAddId) {


        if(operatorId.equals(userToAddId)){
            throw new IllegalArgumentException("不能添加自己到群组");
        }

        boolean isExist = groupMemberRepository.exists(operatorId, userToAddId);
        if (isExist) {
            throw new IllegalArgumentException("成员已存在群组中");
        }
        return GroupMember.builder()
                .groupId(chatGroup.getId())
                .userId(userToAddId)
                .role(GroupMember.Role.MEMBER.getValue())
                .build();
    }

}
