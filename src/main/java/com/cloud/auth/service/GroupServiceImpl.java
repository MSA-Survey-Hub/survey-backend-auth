package com.cloud.auth.service;

import com.cloud.auth.config.exception.BaseException;
import com.cloud.auth.dto.FileDTO;
import com.cloud.auth.dto.GroupDTO;
import com.cloud.auth.dto.PageRequestDTO;
import com.cloud.auth.dto.PageResultDTO;
import com.cloud.auth.dto.group.PatchGroupReq;
import com.cloud.auth.dto.group.PatchGroupRes;
import com.cloud.auth.entity.DelYn;
import com.cloud.auth.entity.Group;
import com.cloud.auth.entity.User;
import com.cloud.auth.entity.UserGroup;
import com.cloud.auth.openfeign.CommonServiceClient;
import com.cloud.auth.repository.GroupRepository;
import com.cloud.auth.repository.UserGroupRepository;
import com.cloud.auth.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.cloud.auth.config.response.BaseResponseStatus.NOT_EXIST_GROUP;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final CommonServiceClient commonServiceClient;

    @Autowired
    private final ModelMapper mapper;

    // 그룹 리스트 조회
    @Override
    public PageResultDTO<GroupDTO, Group> getGroupList(String userId, PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("groupId").descending());
        Page<Group> groupListPage = groupRepository.findExistGroup(pageable);

        Function<Group, GroupDTO> fn = ((group) -> {
            List<User> prtcpList = userGroupRepository.userList(group.getGroupId());
            AtomicReference<String> participatedFlag = new AtomicReference<>("N"); // 참여 여부
            if (prtcpList.size() > 0) {
                prtcpList.forEach((prtcp) -> {
                    if (prtcp.getUserId().equals(userId)) {
                        participatedFlag.set("Y");
                    }
                });
            }

            String isParticipated = participatedFlag.get();
            String isCreated = userId.equals(group.getRegId()) ? "Y" : "N";

            return entityToDTO(group, prtcpList, isParticipated, isCreated);
        });

        return new PageResultDTO<>(groupListPage, fn);
    }


    @Override
    public List<GroupDTO> getAllGroupList(String userId) {
        List<GroupDTO> groupDTOList = new ArrayList<>();
        List<Group> groupList = groupRepository.findByRegId(userId);

        groupList.forEach(group -> {
            GroupDTO groupDTO  = mapper.map(group, GroupDTO.class);
            groupDTO.setPrtcpList(userGroupRepository.userList(group.getGroupId()));
            groupDTOList.add(groupDTO);
        });

        return groupDTOList;
    }


    // 그룹 삭제
    @Override
    public PatchGroupRes deleteGroup(Integer groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_GROUP));
        group.deleteGroup();
        groupRepository.save(group);
        return PatchGroupRes.toDto(group);
    }

    // 그룹 생성
    @Override
    public Integer insertGroup(GroupDTO groupDTO) throws IOException {
        String imageUrl = "";
        if (groupDTO.getGroupImage()!=null) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginalName(groupDTO.getGroupImage().getOriginalFilename());
            fileDTO.setFileBytes(groupDTO.getGroupImage().getBytes());
            imageUrl = commonServiceClient.uploadFile(fileDTO, "group");
            System.out.println("imageUrl = " + imageUrl);
        }
        groupDTO.setGroupImageUrl(imageUrl);
        groupDTO.setDelYn(DelYn.N);
        User user = userRepository.findByUserId(groupDTO.getRegId());
        Group group = dtoToEntity(groupDTO);
        Group save = groupRepository.save(group);
        return group.getGroupId();
    }


    // 그룹 상세 조회
    @Override
    public GroupDTO getOneGroupDetail(Integer groupId) {
        Group group = groupRepository.findByGroupId(groupId).orElseThrow(()->new BaseException(NOT_EXIST_GROUP));
        List<User> userList = userGroupRepository.userList(groupId);
        String isParticipated = null;
        String isCreated = null;
        GroupDTO groupDTO = entityToDTO(group, userList, isParticipated, isCreated);
        return groupDTO;
    }

    // 그룹 검색 (그룹 이름 기준)
    @Override
    public Group findByUserId(String regId) {
        Group group = groupRepository.findByUserId(regId);
        return group;
    }

    @Override
    public Group findByGroupName(String groupName) {
        Group group = groupRepository.findByGroupName(groupName);
        return group;
    }

    @Override
    public PatchGroupRes modifyGroup(PatchGroupReq req){
        Group group = groupRepository.findByGroupId(req.getGroupId())
                .orElseThrow(()-> new BaseException(NOT_EXIST_GROUP));
        // 그룹 정보 수정
        group.modGroup(req);

        // 그룹 속한 인원 추가
        req.getNewGroupUserList().forEach((GroupUser)->{
            User user = userRepository.findByUserId(GroupUser);
            UserGroup userGroup = UserGroup.builder()
                    .group(group)
                    .user(user)
                    .build();
            userGroupRepository.save(userGroup);
        });

        // 그룹에서 인원 제거
        req.getDeleteGroupUserList().forEach((GroupUser)->{
            // 그룹에서 인원 제거하는 API
        });

        groupRepository.save(group);

        return PatchGroupRes.toDto(group);
    }
}
