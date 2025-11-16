package com.it.imdemo.domain.group;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.it.imdemo.domain.group.model.ChatGroup;
import com.it.imdemo.domain.group.model.GroupMember;
import com.it.imdemo.domain.user.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GroupDomainService {

    @Resource
    private GroupMemberRepository groupMemberRepository;


    public void assertCanAddMember(ChatGroup chatGroup, Long operatorId, Long userToAddId) {
        if(operatorId.equals(userToAddId)){
            throw new IllegalArgumentException("不能添加自己到群组");
        }

        if (groupMemberRepository.exists(chatGroup.getId(), operatorId)) {
            throw new IllegalArgumentException("操作用户不是群成员，无法添加成员");
        }

        if (groupMemberRepository.exists(chatGroup.getId(), userToAddId)) {
            throw new IllegalArgumentException("成员已存在群组中");
        }

        chatGroup.assertNotFull();
    }
}
