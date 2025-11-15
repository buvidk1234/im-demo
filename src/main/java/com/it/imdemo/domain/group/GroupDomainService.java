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


    public void assertCanAddMember(ChatGroup chatGroup, Long operatorId, Long userToAddId) {
        if(operatorId.equals(userToAddId)){
            throw new IllegalArgumentException("不能添加自己到群组");
        }

        boolean operatorExists = groupMemberRepository.exists(chatGroup.getId(), operatorId);
        if (!operatorExists) {
            throw new IllegalArgumentException("操作用户不是群成员，无法添加成员");
        }

        boolean isExist = groupMemberRepository.exists(chatGroup.getId(), userToAddId);
        if (isExist) {
            throw new IllegalArgumentException("成员已存在群组中");
        }
    }
}
