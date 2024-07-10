package com.api.aluimoveis.repository;

import com.api.aluimoveis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //@Query(value = "select * from tab_user where email = ?1 limit 1", nativeQuery = true)
    //public User findUserByEmail(String email);

    UserDetails findByEmail(String email);
}
