package com.victortello.ws.shared;

import com.victortello.ws.webservice.shared.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= com.victortello.ws.webservice.shared.Utils.class)
class UtilsTest {

	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGeneratedUserId() {
		String userId = utils.generatedUserId(30);
		String userId2 = utils.generatedUserId(30);

		assertNotNull(userId);
		assertNotNull(userId2);

		assertTrue(userId.length() == 30);
		assertTrue(!userId.equalsIgnoreCase(userId2));
	}

	@Test
	void testHasTokenExpired() {

		String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2Y3RydGVsbG9AZ21haWwuY29tIiwiZXhwIjoxNjI5OTA0NDcyfQ.mydOmh0avgznNYVZRmfxzkh7Yob-3eEQFPHvDtmCXREYV1LnMhM8aZqQ7lftSXEFSmOiPxe_04jkaZyVNmvAbQ";
		boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);

		assertTrue(hasTokenExpired);

	}

	@Test
	final void testHasTokenNotExpired() {
		String token = utils.generateEmailVerificationToken("4yr65hhyid84");
		assertNotNull(token);

		boolean hasTokenExpired = Utils.hasTokenExpired(token);
		assertFalse(hasTokenExpired);
	}

}
