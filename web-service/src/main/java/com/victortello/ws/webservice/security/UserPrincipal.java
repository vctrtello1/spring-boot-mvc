package com.victortello.ws.webservice.security;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.victortello.ws.webservice.io.entity.UserEntity;
import com.victortello.ws.webservice.io.entity.AuthorityEntity;
import com.victortello.ws.webservice.io.entity.RoleEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    static final long serialVersionUID = 1L;

    UserEntity userEntity;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<AuthorityEntity> authoritiesEntities = new ArrayList<>();

        // Get user roles

        Collection<RoleEntity> roles = userEntity.getRoles();

        if (roles == null) {
            return authorities;
        }

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authoritiesEntities.addAll(role.getAuthorities());
        });

        authoritiesEntities.forEach((authorityEntity) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {

        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {

        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return this.userEntity.getEmailVerificationStatus();
    }

}
