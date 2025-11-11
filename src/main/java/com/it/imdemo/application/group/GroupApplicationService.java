package com.it.imdemo.application.group;

import com.it.imdemo.domain.group.GroupDomainService;
import com.it.imdemo.domain.group.GroupMemberRepository;
import com.it.imdemo.domain.group.GroupRepository;
import com.it.imdemo.domain.group.model.ChatGroup;
import com.it.imdemo.domain.group.model.GroupMember;
import com.it.imdemo.domain.user.UserRepository;
import com.it.imdemo.domain.user.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GroupApplicationService {

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupMemberRepository groupMemberRepository;
    @Resource
    private GroupDomainService groupDomainService;

    public Long createGroup(CreateGroupCmd cmd) {
        ChatGroup chatGroup = ChatGroup.create(cmd.getName(),cmd.getDescription(),cmd.getOwnerId(),cmd.getAvatarUrl());
        Long groupId = groupRepository.save(chatGroup);
        GroupMember groupMember = GroupMember.create(groupId,cmd.getOwnerId(), GroupMember.Role.ADMIN);
        groupMemberRepository.save(groupMember);
        return groupId;
    }

    public void addMember(AddMemberCmd cmd) {
        ChatGroup chatGroup = groupRepository.getById(cmd.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        User operator = userRepository.getById(cmd.getOperatorId())
                .orElseThrow(() -> new IllegalArgumentException("Operator not found"));

        User userToAdd= userRepository.getById(cmd.getMemberId())
                        .orElseThrow(()->new IllegalArgumentException("User not found"));

        GroupMember groupMember = groupDomainService.addMemberToGroup(chatGroup, operator, userToAdd);
        groupMemberRepository.save(groupMember);

    }

}
