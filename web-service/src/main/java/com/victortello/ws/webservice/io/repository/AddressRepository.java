package com.victortello.ws.webservice.io.repository;

import java.util.List;

import com.victortello.ws.webservice.io.entity.AddressEntity;
import com.victortello.ws.webservice.io.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository  extends CrudRepository<AddressEntity,Long>{
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

}
