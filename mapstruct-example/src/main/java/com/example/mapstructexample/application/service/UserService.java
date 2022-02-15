package com.example.mapstructexample.application.service;

import com.example.mapstructexample.application.controller.UserDTO;
import com.example.mapstructexample.application.domain.User;
import com.example.mapstructexample.application.mapper.UserMapper;
import com.example.mapstructexample.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private UserMapper userMapper = UserMapper.INSTANCE;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        UserDTO userDTO1 = userMapper.toUserDTO(userRepository.save(user));
        return userDTO1;
    }
}
