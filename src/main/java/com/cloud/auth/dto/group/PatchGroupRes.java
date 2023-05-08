package com.cloud.auth.dto.group;

import com.cloud.auth.entity.DelYn;
import com.cloud.auth.entity.Group;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchGroupRes {

    private Integer groupId;

    private String groupName;

    private Integer groupCode;

    private String groupDescription;

    private Integer groupCnt;

    private String regId;

    private LocalDateTime regDt;

    private String modId;

    private LocalDateTime modDt;

    private DelYn delYn;

    private String groupImageUrl;

    public static PatchGroupRes toDto(Group group){
        return PatchGroupRes.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupCode(group.getGroupCode())
                .groupDescription(group.getGroupDescription())
                .groupCnt(group.getGroupCnt())
                .regId(group.getRegId())
                .regDt(group.getRegDt())
                .modId(group.getModId())
                .delYn(group.getDelYn())
                .groupImageUrl(group.getGroupImageUrl())
                .build();
    }
}
