package com.victortello.ws.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.victortello.ws.webservice.AddressDTO;
import com.victortello.ws.webservice.io.entity.AddressEntity;
import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.AddressRepository;
import com.victortello.ws.webservice.io.repository.UserRepository;
import com.victortello.ws.webservice.service.AddressService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {

        ModelMapper modelMapper = new ModelMapper();

        List<AddressDTO> returnValue = new ArrayList<>();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }

        return returnValue;
    }

}
