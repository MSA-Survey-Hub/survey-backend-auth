package com.cloud.auth.service;

import com.cloud.auth.dto.UserDTO;
import com.cloud.auth.entity.User;
import com.cloud.auth.repository.UserRepository;
import com.cloud.auth.service.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper mapper;


    public UserDTO getUserDetailInfo(String UserId) {
        User user = userRepository.findByUserId(UserId);
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserDetailInfoList(List<Map<String, Object>> list) {
        List<UserDTO> userList = new ArrayList<>();
        list.forEach(user -> {
            userList.add(getUserDetailInfo((String) user.get("reg_id")));
        });
        return userList;
    }

    @Override
    public List<UserDTO> getAllUserList(String type, String keyword) {

        List<UserDTO> userList = new ArrayList<>();
        if (type.equals("Name")){
            List<User> list = userRepository.findByName(keyword);
            list.forEach(user -> {
                userList.add(mapper.map(user, UserDTO.class));
            });
        }else{
            List<User> list = userRepository.findByMailAddr(keyword);
            list.forEach(user -> {
                userList.add(mapper.map(user, UserDTO.class));
            });
        }
        return userList;
    }
}
