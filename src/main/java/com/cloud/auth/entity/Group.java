package com.cloud.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@DynamicInsert
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

    @Column(name = "reg_dt", nullable = false, length = 20)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    @Column(name = "mod_id", length = 20)
    private String modId;

    @Column(name = "mod_dt")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDt;

    @Column(name = "del_yn", nullable = false)
    @ColumnDefault("'N'")
    @Enumerated(EnumType.STRING)
    private DelYn delYn;
}
