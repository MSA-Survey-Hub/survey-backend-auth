package com.cloud.auth.dto.group;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchGroupReq {
    private Integer groupId;
    private String groupName;
    private String groupDescription;
    private List<String> newGroupUserList;
    private List<String> deleteGroupUserList;
}
