package com.it.imdemo.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.group.GroupRepository;
import com.it.imdemo.domain.group.model.ChatGroup;
import com.it.imdemo.infrastructure.persistence.entity.ChatGroupEntity;
import com.it.imdemo.infrastructure.persistence.entity.GroupMemberEntity;
import com.it.imdemo.infrastructure.persistence.mapper.ChatGroupMapper;
import com.it.imdemo.infrastructure.persistence.mapper.GroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;

    @Override
    @Transactional
    public Long save(ChatGroup chatGroup) {
        ChatGroupEntity chatGroupEntity = new ChatGroupEntity();
        BeanUtils.copyProperties(chatGroup, chatGroupEntity);
        chatGroupMapper.insert(chatGroupEntity);
        return chatGroupEntity.getId();
    }

    @Override
    public Optional<ChatGroup> getById(Long groupId) {

        ChatGroupEntity chatGroupEntity = new LambdaQueryChainWrapper<>(chatGroupMapper).eq(ChatGroupEntity::getId, groupId).one();

        return Optional.ofNullable(chatGroupEntity).map(entity -> {
                ChatGroup chatGroup = new ChatGroup();
                BeanUtils.copyProperties(entity, chatGroup);
                return chatGroup;
        });
    }

    @Override
    public Boolean existMemberInGroup(Long chatGroupId, Long memberId) {
        Long count = new LambdaQueryChainWrapper<>(groupMemberMapper).eq(GroupMemberEntity::getGroupId, chatGroupId)
                .eq(GroupMemberEntity::getUserId, memberId)
                .count();
        return count > 0;
    }
}
