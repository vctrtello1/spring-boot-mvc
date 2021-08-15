package com.victortello.ws.webservice.service;

import java.util.List;

import com.victortello.ws.webservice.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
    UserDto updateUser(String id, UserDto user);
    void deleteUser(String id);
    List<UserDto> getUsers(int page, int limit);
    boolean verifyEmailToken(String token);
    
}
