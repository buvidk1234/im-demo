package com.it.imdemo.application.group;

import com.it.imdemo.application.user.UserApplicationService;
import com.it.imdemo.domain.group.GroupDomainService;
import com.it.imdemo.domain.group.GroupMemberRepository;
import com.it.imdemo.domain.group.GroupRepository;
import com.it.imdemo.domain.group.model.ChatGroup;
import com.it.imdemo.domain.group.model.GroupMember;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupApplicationService {

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private UserApplicationService userApplicationService;
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

        userApplicationService.assertAvailable(cmd.getOperatorId());

        userApplicationService.assertAvailable(cmd.getUserToAddId());

        GroupMember groupMember = groupDomainService.addMemberToGroup(chatGroup, cmd.getOperatorId(), cmd.getUserToAddId());

        groupMemberRepository.save(groupMember);
    }

    public List<Long> findMembersOfGroup(long groupId) {
        return groupMemberRepository.findMembersByGroupId(groupId);
    }
}
