package com.Security_1.security_1.config;

import com.Security_1.security_1.entity.UserInfo;
import com.Security_1.security_1.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
@Autowired
    private UserInfoRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//here following 3 lines are for the to map the all the userEntity class and save it to thr UserDetails class...
        Optional<UserInfo> userEntity = userRepository.findByName(username);
        return  userEntity.map(UserEntityUserDetails:: new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found..."));

    }

}

