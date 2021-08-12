package com.victortello.ws.webservice;


import com.victortello.ws.webservice.io.entity.UserEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends PagingAndSortingRepository<UserEntity,Long>{
    
    UserEntity findUserByEmail(String email);
    UserEntity findByUserId(String userId);
}
