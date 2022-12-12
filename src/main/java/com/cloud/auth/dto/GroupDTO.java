package com.cloud.auth.dto;

import com.cloud.auth.entity.DelYn;
import com.cloud.auth.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Integer groupId;
    private String groupName;
    private Integer groupCode;
    private String groupDescription;
    private Integer groupCnt;
    private String regId;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;
    private String modId;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDt;
    private DelYn delYn;
    private String isParticipated; // 참여
    private String isCreated; // 생성
    private List<User> prtcpList; // 그룹 참여자 목록
    private List<String> groupUserList;
    private MultipartFile groupImage;
    private String groupImageUrl;

    public void addPrtcpList(User user) {
        prtcpList.add(user);
    } // 그룹 참여자 추가
}
