package com.it.imdemo.domain.group;

import com.it.imdemo.domain.group.model.GroupMember;

import java.util.Collection;
import java.util.List;

public interface GroupMemberRepository {
    void save(GroupMember groupMember);
    boolean exists(Long groupId, Long userId);

    List<Long> findMembersByGroupId(Long groupId);
}
