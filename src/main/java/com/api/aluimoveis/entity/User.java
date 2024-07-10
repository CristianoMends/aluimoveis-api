package com.api.aluimoveis.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity()
@Table(name="tab_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name = "access",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole access;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Property> properties;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Message> receivedMessages;


    public User() {}

    //get e set-------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.access.equals(UserRole.OWNER)){
            return List.of(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_USER"));
        }else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

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

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }


    public User(String name, String email, String password, UserRole access) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.access = access;
    }
}
