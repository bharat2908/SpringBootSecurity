package com.Security_1.security_1.config;

import com.Security_1.security_1.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
public class UserEntityUserDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    //to pass the UserEntityUserDetail class into the UserEntity so, that's why created a constructor...

    public UserEntityUserDetails(UserInfo userEntity) {
    name = userEntity.getName();
    password = userEntity.getPassword();

    //Little bit changes just check once you understand the logic...
    authorities = Arrays.stream(userEntity.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
        return true;
    }
}
