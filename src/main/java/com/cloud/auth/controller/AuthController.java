package com.cloud.auth.controller;

import com.cloud.auth.dto.UserDTO;
import com.cloud.auth.openfeign.CommonServiceClient;
import com.cloud.auth.service.AuthService;
import com.cloud.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequestMapping(value="v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(UserDTO userDto) {
        if(authService.existsByUsername(userDto.getUserId())) {
            return new ResponseEntity<>("유저가 존재합니다.", HttpStatus.CONFLICT);
        }
        try {
            UserDTO userDTO = authService.createUser(userDto);
            userService.registerUser(userDTO);
        } catch (Exception e) {
            System.out.printf("authService.createUser error message: %s \n",e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("회원가입에 성공했습니다.", HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping(path = "/signin")
    public ResponseEntity authenticateUser(@RequestBody HashMap<String, String> map) {
        AccessTokenResponse tokenRes = authService.setAuth(map);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", tokenRes);
        if(tokenRes != null){
            data.put("info", userService.getUserDetailInfo(map.get("username")));
        }
        Map<String, Object> resultmap = new HashMap<String, Object>();
        resultmap.put("data", data);
        return ResponseEntity.ok(resultmap);
    }

    // refresh token
    @PostMapping(path = "/refresh_token")
    public ResponseEntity refreshToken(@RequestBody  HashMap<String, String> map) {
        String refreshToken = map.get("refresh_token");
        Map<String, Object> tokenRes = authService.refreshToken(refreshToken);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", tokenRes.get("token"));
        if(tokenRes != null){
            data.put("info", userService.getUserDetailInfo((String) tokenRes.get("username")));
        }

        return ResponseEntity.ok(data);
    }

}
