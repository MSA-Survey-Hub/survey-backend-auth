package com.cloud.auth.dto;

import com.cloud.auth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String firstName;
    private String lastName;
    private String job;
    private int age;
    private String gender;
    private String phone;
    private String mailAddr;
    private UserRole userRole;
    private MultipartFile userImage;
    private String imageUrl;
    private String userPwd;
    private int status;
    private String statusInfo;
}
