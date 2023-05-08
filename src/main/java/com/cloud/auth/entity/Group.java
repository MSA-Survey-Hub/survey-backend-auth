package com.cloud.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`group`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    @Column(name = "group_name", nullable = false, length = 30)
    private String groupName;

    @Column(name = "group_code", nullable = false)
    private Integer groupCode;

    @Column(name = "group_description", nullable = false, length = 100)
    private String groupDescription;

    @Column(name = "group_cnt", nullable = false)
    @ColumnDefault("1")
    private Integer groupCnt;

    @JoinColumn(name = "reg_id", nullable = false)
    private String regId;

    @CreatedDate
    @Column(name = "reg_dt", nullable = false, length = 20)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    @Column(name = "mod_id", length = 20)
    private String modId;

    @LastModifiedDate
    @Column(name = "mod_dt")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDt;

    @Column(name = "del_yn", nullable = false)
    @ColumnDefault("'N'")
    @Enumerated(EnumType.STRING)
    private DelYn delYn;

    @Column(name = "group_image_url")
    private String groupImageUrl;

    public void deleteGroup(Integer groupId){ this.delYn = DelYn.Y;};
}
