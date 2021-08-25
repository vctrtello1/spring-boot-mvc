package com.victortello.ws.ui.controller;

import com.victortello.ws.webservice.model.response.UserRest;
import com.victortello.ws.webservice.service.impl.UserServiceImp;
import com.victortello.ws.webservice.ui.controller.userController;
import com.victortello.ws.webservice.shared.dto.UserDto;
import com.victortello.ws.webservice.shared.dto.AddressDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

	@InjectMocks
	userController userController;

	@Mock
	UserServiceImp userServiceImp;

	UserDto userDto;

	final String USER_ID = "bfhry47fhdjd7463gdh";

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.openMocks(this);

		userDto = new UserDto();
		userDto.setFirstName("Victor");
		userDto.setLastName("Tello");
		userDto.setEmail("test@test.com");
		userDto.setEmailVerificationStatus(Boolean.FALSE);
		userDto.setEmailVerificationToken(null);
		userDto.setUserId(USER_ID);
		userDto.setAddresses(getAddressesDto());
		userDto.setEncryptedPassword("xcf58tugh47");
	}

	@Test
	void testGetUser() {
		when(userServiceImp.getUserByUserId(anyString())).thenReturn(userDto);

		UserRest userRest = userController.getUser(USER_ID);

		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertEquals(userDto.getLastName(), userRest.getLastName());
		assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
	}

	private List<AddressDTO> getAddressesDto() {
		AddressDTO addressDto = new AddressDTO();
		addressDto.setType("shipping");
		addressDto.setCity("Vancouver");
		addressDto.setCountry("Canada");
		addressDto.setPostalCode("ABC123");
		addressDto.setStreetName("123 Street name");

		AddressDTO billingAddressDto = new AddressDTO();
		billingAddressDto.setType("billling");
		billingAddressDto.setCity("Vancouver");
		billingAddressDto.setCountry("Canada");
		billingAddressDto.setPostalCode("ABC123");
		billingAddressDto.setStreetName("123 Street name");

		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;

	}

}
