package com.cloud.auth.service;

import com.cloud.auth.dto.UserDTO;
import com.cloud.auth.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface UserService {

    UserDTO getUserDetailInfo(String UserId); // 사용자 정보 조회

    List<UserDTO> getUserDetailInfoList(List<Map<String, Object>> list); // 사용자 정보 리스트 조회

    List<UserDTO> getAllUserList(String type, String keyword); // 사용자 정보 검색 리스트 조회

    void registerUser(UserDTO userDTO) throws IOException;
    void modifyUser(UserDTO userDTO) throws IOException;

    default User dtoToEntity(UserDTO dto, String imageUrl) {
        User user = User.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .job(dto.getJob())
                .age(dto.getAge())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .mailAddr(dto.getMailAddr())
                .imageUrl(imageUrl)
                .mailAcceptYn(true)
                .phoneAcceptYn(true)
                .userRole(dto.getUserRole())
                .build();
        return user;
    }
}
