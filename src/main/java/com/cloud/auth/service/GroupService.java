package com.cloud.auth.service;
import com.cloud.auth.dto.GroupDTO;
import com.cloud.auth.dto.PageRequestDTO;
import com.cloud.auth.dto.PageResultDTO;
import com.cloud.auth.entity.Group;
import com.cloud.auth.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface GroupService {

    // 그룹 상세 정보 조회를 위한 DTO 변환
    GroupDTO getOneGroupDetail(Integer groupId);

    default GroupDTO entityToDTO(Group group, List<User> userList, String isParticipated, String isCreated) {
        GroupDTO groupDTO = GroupDTO.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupDescription(group.getGroupDescription())
                .groupCnt(group.getGroupCnt())
                .prtcpList(new ArrayList<>())
                .regId(group.getUser().getRegId())
                .regDt(group.getRegDt())
                .modId(group.getModId())
                .modDt(group.getModDt())
                .isParticipated(isParticipated)
                .isCreated(isCreated)
                .build();
        userList.forEach(user -> {
            groupDTO.addPrtcpList(user);
        });
        return groupDTO;
    }

    default Group dtoToEntity(GroupDTO dto, User user) {
        Group group = Group.builder()
                .groupId(dto.getGroupId())
                .groupName(dto.getGroupName())
                .groupCode(dto.getGroupCode())
                .groupDescription(dto.getGroupDescription())
                .groupCnt(dto.getGroupCnt())
                .user(user)
                .regDt(dto.getRegDt())
                .modId(dto.getModId())
                .modDt(dto.getModDt())
                .delYn(dto.getDelYn())
                .build();
        return group;
    }

    PageResultDTO<GroupDTO, Group> getGroupList(String userId, PageRequestDTO requestDTO);

    // 그룹 삭제
    Integer deleteGroup(Integer groupId);

    // 그룹 생성
    void insertGroup(GroupDTO groupDTO);

    // 그룹 검색 (개설자 기준)
    Group findByGroupName(String groupName);

    // 그룹 검색 (그룹 이름 기준)
    Group findByUserId(String userId);
}
