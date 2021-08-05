package com.victortello.ws.webservice;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    UserDto createUser(UserDto user);
    
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);

    UserDto updateUser(String id, UserDto user);
    
}
