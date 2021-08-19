package com.victortello.ws.webservice.io.repository;

import com.victortello.ws.webservice.io.entity.PasswordResetTokenEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity,Long>{
    
}
