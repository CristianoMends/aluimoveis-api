package com.api.aluimoveis.dto;

import com.api.aluimoveis.entity.User;
import com.api.aluimoveis.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "access is mandatory")
    private UserRole access;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getAccess() {
        return access;
    }

    public void setAccess(UserRole access) {
        this.access = access;
    }

    /*Constructor*/

    public UserDto(String name, String email, String password, UserRole access) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.access = access;
    }

    public User toEntity(){
        return new User(name, email,password, access);
    }
}
