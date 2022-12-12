package com.cloud.auth.service;

import com.cloud.auth.dto.FileDTO;
import com.cloud.auth.dto.UserDTO;
import com.cloud.auth.entity.User;
import com.cloud.auth.openfeign.CommonServiceClient;
import com.cloud.auth.repository.UserRepository;
import com.cloud.auth.service.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper mapper;

    private final CommonServiceClient commonServiceClient;

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

    @Override
    public void registerUser(UserDTO userDTO) throws IOException {
        String imageUrl = "";
        if (userDTO.getUserImage()!=null) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginalName(userDTO.getUserImage().getOriginalFilename());
            fileDTO.setFileBytes(userDTO.getUserImage().getBytes());
            imageUrl = commonServiceClient.uploadFile(fileDTO, "user");
            System.out.println("imageUrl = " + imageUrl);

        }
        User user = dtoToEntity(userDTO, imageUrl);
        userRepository.save(user);
    }

    @Override
    public void modifyUser(UserDTO userDTO) throws IOException {
        User user = userRepository.findByUserId(userDTO.getUserId());

        // user info 변경하기
        user.changeMailAddr(userDTO.getMailAddr());
        user.changeName(userDTO.getName());
        user.changeAge(userDTO.getAge());
        user.changeGender(userDTO.getGender());
        user.changeJob(userDTO.getJob());
        user.changePhone(userDTO.getPhone());

        // user image 변경하기
        if (userDTO.getUserImage() != null) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginalName(userDTO.getUserImage().getOriginalFilename());
            fileDTO.setFileBytes(userDTO.getUserImage().getBytes());
            String imageUrl = commonServiceClient.uploadFile(fileDTO, "user");
            user.changeImageUrl(imageUrl);
        }

        userRepository.save(user);
    }

    @Override
    public List<UserDTO> searchUserbyUserId(String searchContent) {
        List<User> userList = userRepository.searchUserbyUserId(searchContent);
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user -> {
            UserDTO userDTO = entityToDTO(user);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }

    @Override
    public List<UserDTO> searchUserbyName(String searchContent) {
        List<User> userList = userRepository.searchUserbyName(searchContent);
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user -> {
            UserDTO userDTO = entityToDTO(user);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }
}
