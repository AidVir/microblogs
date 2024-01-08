package com.aidas.microblogs.service;

import com.aidas.microblogs.dto.JwtResponse;
import com.aidas.microblogs.dto.UserCredentialsDto;
import com.aidas.microblogs.exception.ResourceNotFoundException;
import com.aidas.microblogs.exception.ValidationException;
import com.aidas.microblogs.model.User;
import com.aidas.microblogs.repository.UserRepository;
import com.aidas.microblogs.security.jwt.JwtUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
  }

  public void registerUser(UserCredentialsDto signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new ValidationException("Error: Username is already taken!");
    }

    if (signUpRequest.getUsername().length() < 3) {
      throw new ValidationException("Error: Username must be at least 3 characters long!");
    }

    if (signUpRequest.getPassword().length() < 6) {
      throw new ValidationException("Error: Password must be at least 6 characters long!");
    }

    User user = new User(signUpRequest.getUsername(),
      encoder.encode(signUpRequest.getPassword()));

    userRepository.save(user);
  }
  public void updateUser(String username, Long id) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    user.setUsername(username);
    userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public JwtResponse authenticateUser(UserCredentialsDto loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    return new JwtResponse(jwt,
      userDetails.getId(),
      userDetails.getUsername());
  }
}
