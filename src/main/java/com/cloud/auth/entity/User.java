package com.cloud.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
        @Id
        @Comment("사용자 아이디")
        @Column(name = "user_id", nullable = false)
        private String userId;

        @Comment("사용자 이름")
        @Column(name="name", nullable = false)
        private String name;

        @Comment("사용자 직업")
        @Column(name="job", nullable = false)
        private String job;

        @Comment("사용자 나이")
        @Column(name = "age", nullable = false)
        private int age;

        @Comment("사용자 성별")
        @Column(name="gender", nullable = false)
        private String gender;

        @Comment("사용자 전화번호")
        @Column(name = "phone", nullable = false)
        private String phone;

        @Comment("사용자 이메일 주소")
        @Column(name = "mail_addr", nullable = false)
        private String mailAddr;

        @Comment("사용자 프로필 사진")
        @Column(name = "image_url", nullable = false)
        private String imageUrl;

        @Comment("사용자 이메일 수신동의 여부")
        @ColumnDefault("true")
        @Column(name = "is_mail_accept", nullable = false)
        private boolean mailAcceptYn;

        @Comment("사용자 전화번호 수신동의 여부")
        @ColumnDefault("true")
        @Column(name = "is_phone_accept", nullable = false)
        private boolean phoneAcceptYn;

        @Comment("사용자 권한")
        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        private UserRole userRole;

        @Comment("등록자")
        @Column(name = "reg_id", nullable = false)
        private String regId;

        @CreatedDate
        @Comment("등록일자")
        @Column(name = "reg_dt", nullable = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime regDt;

        @ColumnDefault("false")
        @Comment("사용자 탈퇴 여부")
        @Column(name = "use_yn", nullable = false)
        private boolean useYn;


}
