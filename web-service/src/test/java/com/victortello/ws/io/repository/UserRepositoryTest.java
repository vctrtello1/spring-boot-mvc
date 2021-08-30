package com.victortello.ws.io.repository;

import com.victortello.ws.webservice.io.entity.AddressEntity;
import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.UserRepository;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.victortello.ws.webservice.io.repository.UserRepository.class)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	static boolean recordsCreated = false;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {

		if (!recordsCreated) {
			createRecrods();

		}

	}

	private void createRecrods() {
		// Prepare User Entity
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("Victor");
		userEntity.setLastName("Tello");
		userEntity.setUserId("1a2b3c");
		userEntity.setEncryptedPassword("puma18ar");
		userEntity.setEmail("victorhugotello@hotmail.com");
		userEntity.setEmailVerificationStatus(true);

		// Prepare User Addresses
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setType("shipping");
		addressEntity.setAddressId("ahgyt74hfy");
		addressEntity.setCity("Vancouver");
		addressEntity.setCountry("Canada");
		addressEntity.setPostalCode("ABCCDA");
		addressEntity.setStreetName("123 Street Address");

		List<AddressEntity> addresses = new ArrayList<>();
		addresses.add(addressEntity);

		userEntity.setAddresses(addresses);

		userRepository.save(userEntity);

		// Prepare User Entity
		UserEntity userEntity2 = new UserEntity();
		userEntity2.setFirstName("Hugo");
		userEntity2.setLastName("Miramontes");
		userEntity2.setUserId("1a2b3cddddd");
		userEntity2.setEncryptedPassword("puma18ar");
		userEntity2.setEmail("vctrtello@gmail.com");
		userEntity2.setEmailVerificationStatus(true);

		// Prepare User Addresses
		AddressEntity addressEntity2 = new AddressEntity();
		addressEntity2.setType("shipping");
		addressEntity2.setAddressId("ahgyt74hfywwww");
		addressEntity2.setCity("Vancouver");
		addressEntity2.setCountry("Canada");
		addressEntity2.setPostalCode("ABCCDA");
		addressEntity2.setStreetName("123 Street Address");

		List<AddressEntity> addresses2 = new ArrayList<>();
		addresses2.add(addressEntity2);

		userEntity2.setAddresses(addresses2);

		userRepository.save(userEntity2);

		recordsCreated = true;

	}

}
