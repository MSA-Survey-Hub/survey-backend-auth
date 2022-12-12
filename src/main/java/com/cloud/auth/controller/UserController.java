package com.cloud.auth.controller;

import com.cloud.auth.dto.UserDTO;
import com.cloud.auth.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="v1/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserDetail(@RequestParam(value = "user_id") String UserId) {
        return new ResponseEntity<>(userService.getUserDetailInfo(UserId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/survey_users_analysis", method = RequestMethod.POST)
    public ResponseEntity<List<UserDTO>> getSurveyUsersAnalysis(@RequestBody List<Map<String,Object>> list) {
        return new ResponseEntity<>(userService.getUserDetailInfoList(list), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/allUserList", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUserList(@RequestParam(value = "type") String type, @RequestParam(value = "keyword") String keyword) {
        return new ResponseEntity<>(userService.getAllUserList(type, keyword), HttpStatus.CREATED);
    }

    @PostMapping("/modify")
    public ResponseEntity<String> modifyUser(UserDTO userDTO) throws IOException {
        try {
            userService.modifyUser(userDTO);
            return new ResponseEntity<>("회원 정보를 수정하였습니다.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public List<UserDTO> searchUser(
            @RequestParam("searchOption") String searchOption,
            @RequestParam("searchContent") String searchContent) {
        if (searchOption.equals("userId")) {
            return userService.searchUserbyUserId(searchContent);
        } else if (searchOption.equals("name")) {
            return userService.searchUserbyName(searchContent);
        } return null;
    }

}
