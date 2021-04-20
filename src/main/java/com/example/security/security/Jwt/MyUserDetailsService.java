package com.example.security.security.Jwt;


import com.example.security.model.user.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) {

    Optional<User> user = userRepository.findUserByUsername(username);

    user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

    return userRepository.findUserByUsername(username).get();
  }


}
