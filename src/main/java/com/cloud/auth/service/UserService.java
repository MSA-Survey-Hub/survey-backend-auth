package com.cloud.auth.service;

import com.cloud.auth.dto.UserDTO;

import java.util.List;
import java.util.Map;


public interface UserService {

    UserDTO getUserDetailInfo(String UserId); // 사용자 정보 조회

    List<UserDTO> getUserDetailInfoList(List<Map<String, Object>> list); // 사용자 정보 리스트 조회


    List<UserDTO> getAllUserList(String type, String keyword); // 사용자 정보 검색 리스트 조회


}
