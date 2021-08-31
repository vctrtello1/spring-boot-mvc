package com.victortello.ws.webservice.io.repository;

import com.victortello.ws.webservice.io.entity.AuthorityEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
