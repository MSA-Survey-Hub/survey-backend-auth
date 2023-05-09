package com.cloud.auth.service;

import com.cloud.auth.config.exception.BaseException;
import com.cloud.auth.config.response.BaseResponseStatus;
import com.cloud.auth.entity.Group;
import com.cloud.auth.entity.User;
import com.cloud.auth.entity.UserGroup;
import com.cloud.auth.repository.GroupRepository;
import com.cloud.auth.repository.UserGroupRepository;
import com.cloud.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService{

    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    // 그룹 참여(user-group table에 값 추가)
    @Override
    public void participateGroup(String userId, Integer groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_EXIST_GROUP));

        UserGroup userGroup = UserGroup.builder()
                        .group(group)
                        .user(userRepository.findByUserId(userId))
                        .build();
        userGroupRepository.save(userGroup);

        // 사용자가 해당 그룹에 참여시 그룹 참여자 수 +1
        groupRepository.updateGroupCnt(groupId);
    }

    @Override
    @Transactional
    public void unparticipateGroup(String userId, Integer groupId){
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NOT_EXIST_GROUP));
        User user = userRepository.findByUserId(userId);
        userGroupRepository.deleteByGroupAndUser(group,user);
    }

    // 그룹 참여자 여부 조회 -> for groupDetail 조회 권한
    @Override
    public boolean isParticipated(String userId, Integer groupId) {
        List<User> userList = userGroupRepository.userList(groupId);
        if(userList.contains(userId)) {
            return true; // 참여자가 그룹에 있음
        }
        else {
            return false; // 참여자가 그룹에 없음
        }
    }
}
