package com.api.aluimoveis.entity;

public enum UserRole {

    OWNER("Owner"),
    USER("User");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
