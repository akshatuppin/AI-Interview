package com.interview.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.interview.entity.User;
import com.interview.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
    
    private final UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        
        User user = userRepository  
                    .findByEmail(email)
                    .orElseThrow(()-> 
                        new UsernameNotFoundException("User not Found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), 
                    user.getPassword(), 
                    List.of(
                        new SimpleGrantedAuthority(
                            user.getRole().getRoleName()
                        )
                    )
                );

    }
    
} 
