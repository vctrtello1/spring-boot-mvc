package com.victortello.ws.webservice;

import java.util.Arrays;
import java.util.Collection;

import com.victortello.ws.webservice.io.entity.AuthorityEntity;
import com.victortello.ws.webservice.io.entity.RoleEntity;
import com.victortello.ws.webservice.io.repository.AuthorityRepository;
import com.victortello.ws.webservice.io.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");

        AuthorityEntity readAuthority = createAuEntity("READ_AUTHORITY");

        AuthorityEntity writeAuthority = createAuEntity("WRITE_AUTHORITY");

        AuthorityEntity deleteAuthority = createAuEntity("DELETE_AUTHORITY");

        createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));

        createRole("ADMIN_USER", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

    }

    @Transactional
    private AuthorityEntity createAuEntity(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);

        }

        return authority;

    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;

    }
}
