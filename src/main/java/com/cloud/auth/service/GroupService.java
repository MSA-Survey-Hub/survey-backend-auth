package com.cloud.auth.service;
import com.cloud.auth.dto.GroupDTO;
import com.cloud.auth.dto.PageRequestDTO;
import com.cloud.auth.dto.PageResultDTO;
import com.cloud.auth.dto.group.PatchGroupReq;
import com.cloud.auth.dto.group.PatchGroupRes;
import com.cloud.auth.entity.DelYn;
import com.cloud.auth.entity.Group;
import com.cloud.auth.entity.User;

import java.io.IOException;
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
                .regId(group.getRegId())
                .regDt(group.getRegDt())
                .modId(group.getModId())
                .modDt(group.getModDt())
                .isParticipated(isParticipated)
                .isCreated(isCreated)
                .groupImageUrl(group.getGroupImageUrl())
                .build();
        userList.forEach(user -> {
            groupDTO.addPrtcpList(user);
        });
        return groupDTO;
    }

    default Group dtoToEntity(GroupDTO dto) {
        Group group = Group.builder()
                .groupId(dto.getGroupId())
                .groupName(dto.getGroupName())
                .groupCode(dto.getGroupCode())
                .groupDescription(dto.getGroupDescription())
                .groupCnt(dto.getGroupCnt())
                .regId(dto.getRegId())
                .delYn(dto.getDelYn())
                .groupImageUrl(dto.getGroupImageUrl())
                .build();
        return group;
    }

    PageResultDTO<GroupDTO, Group> getGroupList(String userId, PageRequestDTO requestDTO);

    List<GroupDTO> getAllGroupList(String userId);

    PatchGroupRes modifyGroup(PatchGroupReq patchGroupReq);

    // 그룹 삭제
    PatchGroupRes deleteGroup(Integer groupId);

    // 그룹 생성
    Integer insertGroup(GroupDTO groupDTO) throws IOException;

    // 그룹 검색 (개설자 기준)
    Group findByGroupName(String groupName);

    // 그룹 검색 (그룹 이름 기준)
    Group findByUserId(String userId);
}
