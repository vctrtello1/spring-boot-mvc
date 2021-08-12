package com.victortello.ws.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.victortello.ws.webservice.AddressDTO;
import com.victortello.ws.webservice.service.AddressService;

public class AddressServiceImp implements AddressService {

    @Override
    public List<AddressDTO> getAddresses(String userId) {        
        List<AddressDTO> returnValue = new ArrayList<>();
        return returnValue;
    }
    
}
