package com.victortello.ws.webservice.service;

import java.util.List;

import com.victortello.ws.webservice.shared.dto.AddressDTO;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
