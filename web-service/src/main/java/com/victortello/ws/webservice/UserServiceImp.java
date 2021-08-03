package com.victortello.ws.webservice;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity storedUserDetails = userRepository.findUserByEmail(user.getEmail());

        if(storedUserDetails != null) throw new RuntimeException("Record already exists");

        String publicUserId = utils.generatedUserId(30);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(publicUserId);
        UserEntity storeUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storeUserDetails, returnValue);

        return returnValue;
    }
    
}
