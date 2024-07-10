package com.api.aluimoveis.service;

import com.api.aluimoveis.dto.UserDto;
import com.api.aluimoveis.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    User save(UserDto user);

    UserDetails findByEmail(String email);

    Optional<User> findById(Long id);

    User updateUser(Long id, UserDto userDto);


}
