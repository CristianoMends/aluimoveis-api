package com.api.aluimoveis.dto;

public class Token {
    private String Token;

    public Token(String token){
        this.Token = token;
    }
    public Token(){

    }
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
