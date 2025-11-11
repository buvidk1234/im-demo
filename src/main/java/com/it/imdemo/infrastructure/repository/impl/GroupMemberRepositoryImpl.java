package com.it.imdemo.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.group.GroupMemberRepository;
import com.it.imdemo.domain.group.model.GroupMember;
import com.it.imdemo.infrastructure.repository.entity.GroupMemberEntity;
import com.it.imdemo.infrastructure.repository.mapper.GroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    @Resource
    private GroupMemberMapper groupMemberMapper;

    @Override
    public boolean exists(Long groupId, Long userId) {
        return groupMemberMapper.exists(new LambdaQueryWrapper<GroupMemberEntity>()
                .eq(GroupMemberEntity::getGroupId, groupId)
                .eq(GroupMemberEntity::getUserId, userId));
    }

    @Override
    public List<Long> findByGroupId(Long groupId) {
        return new LambdaQueryChainWrapper<>(groupMemberMapper)
                .eq(GroupMemberEntity::getGroupId, groupId)
                .list()
                .stream().map(GroupMemberEntity::getId).toList();
    }

    @Override
    public void save(GroupMember groupMember) {
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        BeanUtils.copyProperties(groupMember, groupMemberEntity);
        groupMemberMapper.insert(groupMemberEntity);
    }
}
