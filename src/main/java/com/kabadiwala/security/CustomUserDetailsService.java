package com.kabadiwala.security;
import com.kabadiwala.entity.User;
import com.kabadiwala.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String contactNo) {
        User user = userRepository.findByContactNumber(contactNo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with contact no. = "+contactNo));

        return new CustomUserDetails(user);
    }

}
