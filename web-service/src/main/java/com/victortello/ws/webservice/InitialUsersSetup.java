package com.victortello.ws.webservice;

import java.util.Arrays;
import java.util.Collection;

import com.victortello.ws.webservice.io.entity.AuthorityEntity;
import com.victortello.ws.webservice.io.entity.RoleEntity;
import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.repository.AuthorityRepository;
import com.victortello.ws.webservice.io.repository.RoleRepository;
import com.victortello.ws.webservice.io.repository.UserRepository;
import com.victortello.ws.webservice.shared.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");

        AuthorityEntity readAuthority = createAuEntity("READ_AUTHORITY");

        AuthorityEntity writeAuthority = createAuEntity("WRITE_AUTHORITY");

        AuthorityEntity deleteAuthority = createAuEntity("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));

        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) {
            return;
        }

        UserEntity adminUser = new UserEntity();

        adminUser.setFirstName("victor");
        adminUser.setLastName("tello");
        adminUser.setEmail("victorhugotello@hotmail.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setUserId(utils.generatedUserId(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("marrucus"));
        adminUser.setRoles(Arrays.asList(roleUser, roleAdmin));

        userRepository.save(adminUser);

        UserEntity simpleUser = new UserEntity();

        simpleUser.setFirstName("victor");
        simpleUser.setLastName("tello");
        simpleUser.setEmail("vctrtello@gmail.com");
        simpleUser.setEmailVerificationStatus(true);
        simpleUser.setUserId(utils.generatedUserId(30));
        simpleUser.setEncryptedPassword(bCryptPasswordEncoder.encode("marrucus"));
        simpleUser.setRoles(Arrays.asList(roleUser));

        userRepository.save(simpleUser);

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
