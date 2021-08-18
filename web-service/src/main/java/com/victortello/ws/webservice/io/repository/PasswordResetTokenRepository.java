package com.victortello.ws.webservice.io.repository;

import com.victortello.ws.webservice.io.entity.PasswordResetTokenEntity;

import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity,Long>{
    
}
