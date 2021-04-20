package com.example.security.service;

import com.example.security.model.ConfirmationToken;
import com.example.security.model.user.User;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class SignUpService {


  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  public String signUpUser(User user) {
    boolean userExists = userRepository
        .findByEmail(user.getEmail())
        .isPresent();

    if (userExists) {
      // TODO check of attributes are the same and
      // TODO if email not confirmed send confirmation email.

      throw new IllegalStateException("email already taken");
    }

    String encodedPassword = bCryptPasswordEncoder
        .encode(user.getPassword());

    user.setPassword(encodedPassword);

    userRepository.save(user);

    String token = UUID.randomUUID().toString();

    ConfirmationToken confirmationToken = new ConfirmationToken(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusHours(24),
        user
    );

    confirmationTokenService.saveConfirmationToken(
        confirmationToken);

    return token;
  }
}
