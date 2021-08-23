package com.victortello.ws.webservice.service.imp;

import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.UserRepository;
import com.victortello.ws.webservice.service.impl.UserServiceImp;
import com.victortello.ws.webservice.shared.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImpTest {

	@InjectMocks
	UserServiceImp userServiceImp;

	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testGetUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setFirstName("Victor");
		userEntity.setLastName("Tello");
		userEntity.setEncryptedPassword("marrucus");
		when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userServiceImp.getUser("test@test.com");

		assertNotNull(userDto);
		assertEquals("Victor", userDto.getFirstName());
	}

}
