package com.victortello.ws.webservice;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";    

    public String generatedUserId(int lenght){
        return generateRandomString(lenght);
    }

    public String generatedAdressId(int lenght) {
        return generateRandomString(lenght);
    }

    public String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);

    }
    
}
