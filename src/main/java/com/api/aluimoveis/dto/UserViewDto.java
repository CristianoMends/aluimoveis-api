package com.api.aluimoveis.dto;

import com.api.aluimoveis.entity.Message;
import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.entity.UserRole;

import java.util.List;
import java.util.Set;

public class UserViewDto {
    private Long id;
    private String name;
    private String email;
    private UserRole access;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public UserRole getAccess() {
        return access;
    }

    public void setAccess(UserRole access) {
        this.access = access;
    }


    public UserViewDto(Long id, String name, String email, UserRole access) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.access = access;
    }
}
