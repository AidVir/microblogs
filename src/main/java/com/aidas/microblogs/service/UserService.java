package com.aidas.microblogs.service;

import com.aidas.microblogs.exception.ResourceNotFoundException;
import com.aidas.microblogs.model.User;
import com.aidas.microblogs.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
  }

  public void createUser(User newUser) {
    userRepository.save(newUser);
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

}
