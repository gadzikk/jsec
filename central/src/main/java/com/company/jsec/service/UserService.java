package com.company.jsec.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.builder()
                .username(username)
                .password("$2a$10$.cco1gzdJ.uMv/USXdH/teJK.cscP9bgIgMe1PXpM7o3VLW.Ke/fG")
                .roles("USER")
                .build();
    }
}
