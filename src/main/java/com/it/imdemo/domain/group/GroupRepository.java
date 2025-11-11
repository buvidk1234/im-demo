package com.it.imdemo.domain.group;

import com.it.imdemo.domain.group.model.ChatGroup;

import java.util.Optional;

public interface GroupRepository {
    Long save(ChatGroup chatGroup);

    Optional<ChatGroup> getById(Long groupId);

    Boolean existMemberInGroup(Long chatGroupId, Long memberId);
}
