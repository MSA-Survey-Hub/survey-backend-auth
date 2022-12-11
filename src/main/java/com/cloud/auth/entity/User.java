package com.cloud.auth.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(catalog = "user_db")
public class User {

        public enum AcceptYn { Y, N }

        @Id
        @Column(name = "user_id")
        private String userId;

        @Column(name="name", nullable = false, length = 30)
        private String name;

        @Column(name="job", length = 30)
        private String job;

        @Column(name = "age", nullable = false)
        private int age;

        @Column(name="gender", nullable = false)
        private String gender;

        @Column(name = "phone", length = 11)
        private String phone;

        @Column(name = "mail_addr", length = 30, nullable = false)
        private String mailAddr;

        @Column(name = "is_mail_accept", nullable = false)
        @Enumerated(EnumType.STRING)
        private AcceptYn mailAcceptYn;

        @Column(name = "is_phone_accept", nullable = false)
        @Enumerated(EnumType.STRING)
        private AcceptYn phoneAcceptYn;

        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        private UserRole userRole;

        @Column(name = "reg_id", nullable = false, length = 20)
        private String regId;

        @Column(name = "reg_dt", nullable = false)
        private LocalDateTime regDt;

        @Column(name = "use_yn", nullable = false)
        private String useYn;


}
