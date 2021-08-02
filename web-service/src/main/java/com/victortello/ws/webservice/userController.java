package com.victortello.ws.webservice;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class userController {

    @GetMapping()
    public String getUser(){
        return "get user was called";
    }

    @PostMapping()
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        return null;
    }    

    @PutMapping()
    public String updateUser(){
        return "update user was created";
    }

    @DeleteMapping()
    public String deleteUser() {
        return "delete user was created";
    }
}
