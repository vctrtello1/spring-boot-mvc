package com.victortello.ws.webservice.service.imp;

import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.UserRepository;
import com.victortello.ws.webservice.service.impl.UserServiceImp;
import com.victortello.ws.webservice.shared.dto.UserDto;
import com.victortello.ws.webservice.shared.dto.AddressDTO;
import com.victortello.ws.webservice.shared.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceImpTest {

	@InjectMocks
	UserServiceImp userServiceImp;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	String userId = "hhty57ehfy";
	String encryptedPassword = "74hghd8474jf";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		userEntity.setId(1L);
		userEntity.setFirstName("Victor");
		userEntity.setLastName("Tello");
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("test@test.com");
		userEntity.setEmailVerificationToken("7htnfhr758");		
	}

	@Test
	void testGetUser() {				
		when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userServiceImp.getUser("test@test.com");

		assertNotNull(userDto);
		assertEquals("Victor", userDto.getFirstName());
	}

	@Test
	final void testGetUser_UsernameNotFoundException() {
		when(userRepository.findUserByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userServiceImp.getUser("test@test.com");
		});
	}

	@Test
	final void testCreateUser(){
		when(userRepository.findUserByEmail(anyString())).thenReturn(null);
		when(utils.generatedUserId(anyInt())).thenReturn(userId);
		when(utils.generatedAddressId(anyInt())).thenReturn("hgfnghtyrir884");
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		AddressDTO addressDto = new AddressDTO();
		addressDto.setType("shipping");

		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDto);
		
		UserDto userDto = new UserDto();
		userDto.setAddresses(addresses);
		UserDto storedUserDetails = userServiceImp.createUser(userDto);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
	}

}
