package com.cloud.auth.controller;

import com.cloud.auth.config.response.BaseResponse;
import com.cloud.auth.dto.*;
import com.cloud.auth.dto.group.PatchGroupReq;
import com.cloud.auth.dto.group.PatchGroupRes;
import com.cloud.auth.entity.Group;
import com.cloud.auth.service.GroupService;
import com.cloud.auth.service.UserGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="v1/group")
@Slf4j
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private final GroupService groupService;

    @Autowired
    private final UserGroupService userGroupService;

    @PostMapping("/list")
    public ResponseEntity<PageResultDTO<GroupDTO, Group>> getGroupList(Principal principal, PageRequestDTO pageRequestDTO) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userId = token.getTokenAttributes().get("preferred_username").toString();

        PageResultDTO<GroupDTO, Group> groupList = groupService.getGroupList(userId, pageRequestDTO);
        //System.out.println(token.getTokenAttributes());
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @GetMapping("/allList")
    public ResponseEntity<List<GroupDTO>> getAllGroupList(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userId = token.getTokenAttributes().get("preferred_username").toString();

        List<GroupDTO> groupList = groupService.getAllGroupList(userId);

        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public BaseResponse<PatchGroupRes> delGroup(@RequestParam("groupId") Integer groupId){
        PatchGroupRes patchGroupRes = groupService.deleteGroup(groupId);

        return new BaseResponse<>(patchGroupRes);
    }

    // 그룹 생성
    @PostMapping("/reg")
    public ResponseEntity<String> regGroup(GroupDTO groupDTO) throws IOException {
        System.out.println(groupDTO);
        try {
            System.out.print(groupDTO);
            Integer groupId = groupService.insertGroup(groupDTO);

            // 그룹 참여자 목록 등록하기(그룹 생성자)
            userGroupService.participateGroup(groupDTO.getRegId(), groupId);

            // 그룹 참여자 목록 등록하기(그룹 참여자)
            groupDTO.getGroupUserList().forEach((userId) -> {
                System.out.printf("\n userId: %s",userId);
                userGroupService.participateGroup(userId, groupId);
            });
            return new ResponseEntity<>("그룹 생성에 성공했습니다", HttpStatus.CREATED);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("그룹 생성에 실패했습니다", HttpStatus.BAD_REQUEST);
        }
    }

    // 그룹 수정
    @PatchMapping("/mod")
    public PatchGroupRes modGroup(PatchGroupReq patchGroupReq){
        System.out.printf("\n 그룹 아이디: %s",patchGroupReq.getGroupId());
        System.out.printf("\n 그룹 이름: %s",patchGroupReq.getGroupName());
        System.out.printf("\n 그룹 설명: %s",patchGroupReq.getGroupDescription());
        System.out.printf("\n 그룹에 추가한 사람: %s",patchGroupReq.getNewGroupUserList());
        System.out.printf("\n 그룹으로부터 제거한 사람: %s", patchGroupReq.getDeleteGroupUserList());

        PatchGroupRes patchGroupRes = groupService.modifyGroup(patchGroupReq);
        return patchGroupRes;
    }

    // 그룹 참여(user-group table에 값 추가)
    @PostMapping("/participate")
    public void participateGroup(
            @RequestParam(value = "groupId") Integer groupId,
            @RequestParam(value = "userId") String userId) {
        userGroupService.participateGroup(userId, groupId);
    }

    // 그룹 참여자 여부 조회 -> for groupDetail 조회 권한 / 참여자 있음 : true, 참여자 없음 : false 반환
    @PostMapping("/isParticipated")
    public boolean isParticipated(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "groupId") Integer groupId) {
        return userGroupService.isParticipated(userId, groupId);
    }


    @GetMapping("/detail/{groupId}")
    public GroupDTO oneGroupDetail(@PathVariable Integer groupId) {
        GroupDTO group = groupService.getOneGroupDetail(groupId);
        return group;
    }

    @GetMapping("/searchId/{regId}")
    public Group findByUserId(@PathVariable String regId) {
        Group group = groupService.findByUserId(regId);
        return group;
    }

    @GetMapping("/searchName/{groupName}")
    public Group findByGroupName(@PathVariable String groupName) {
        Group group = groupService.findByGroupName(groupName);
        return group;
    }
}

