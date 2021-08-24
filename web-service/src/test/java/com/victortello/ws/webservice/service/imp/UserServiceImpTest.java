package com.victortello.ws.webservice.service.imp;

import com.victortello.ws.webservice.io.entity.AddressEntity;
import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.UserRepository;
import com.victortello.ws.webservice.service.impl.UserServiceImp;
import com.victortello.ws.webservice.shared.dto.UserDto;
import com.victortello.ws.webservice.shared.dto.AddressDTO;
import com.victortello.ws.webservice.shared.Utils;
import com.victortello.ws.webservice.exceptions.UserServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.victortello.ws.webservice.shared.AmazonSES;

class UserServiceImpTest {

	@InjectMocks
	UserServiceImp userServiceImp;

	@Mock
	UserRepository userRepository;

	@Mock
	AmazonSES amazonSES;

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
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Victor");
		userEntity.setLastName("Tello");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("vctrtello@gmail.com");
		userEntity.setEmailVerificationToken("7htnfhr758");
		userEntity.setAddresses(getAddressesEntity());
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
	final void testCreateUser_CreateUserServiceException() {

		when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("victor");
		userDto.setLastName("tello");
		userEntity.setUserId(userId);
		userDto.setPassword("duende125");
		userDto.setEmail("vct@gmail.com");

		assertThrows(UserServiceException.class, () -> {
			userServiceImp.createUser(userDto);
		});
	}

	@Test
	final void testCreateUser() {
		when(userRepository.findUserByEmail(anyString())).thenReturn(null);
		when(utils.generatedUserId(anyInt())).thenReturn(userId);
		when(utils.generatedAddressId(anyInt())).thenReturn("hgfnghtyrir884");
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("victor");
		userDto.setLastName("tello");
		userEntity.setUserId(userId);
		userDto.setPassword("duende125");
		userDto.setEmail("vctrtello@gmail.com");

		UserDto storedUserDetails = userServiceImp.createUser(userDto);
		assertNotNull(storedUserDetails);

		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
		verify(utils, times(storedUserDetails.getAddresses().size())).generatedAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("duende125");
		verify(userRepository, times(1)).save(any(UserEntity.class));

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

	private List<AddressEntity> getAddressesEntity() {
		List<AddressDTO> addresses = getAddressesDto();

		Type listType = new TypeToken<List<AddressEntity>>() {
		}.getType();

		return new ModelMapper().map(addresses, listType);
	}

}
