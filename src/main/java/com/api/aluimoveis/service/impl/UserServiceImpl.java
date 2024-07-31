package com.api.aluimoveis.service.impl;

import com.api.aluimoveis.dto.UserDto;
import com.api.aluimoveis.entity.User;
import com.api.aluimoveis.exception.BusinessException;
import com.api.aluimoveis.repository.UserRepository;
import com.api.aluimoveis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service()
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("User not found"));

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getAccess() != null) {
            user.setAccess(userDto.getAccess());
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetails findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    Optional<User> getById(Long id) {
        return this.userRepository.findById(id);
    }

    public User save(UserDto user) {
        User userToCreate = user.toEntity();

        return this.userRepository.save(userToCreate);
    }


    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }
}
