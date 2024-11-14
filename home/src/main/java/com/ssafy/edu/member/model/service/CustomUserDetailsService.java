package com.ssafy.edu.member.model.service;

import com.ssafy.edu.member.MemberDto;
import com.ssafy.edu.member.model.repository.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto member = memberMapper.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.withUsername(member.getUserId())
                   .password(member.getUserPwd())
                   .roles("USER")
                   .build();
    }
}
