package com.api.aluimoveis.controller;

import com.api.aluimoveis.dto.*;
import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.entity.User;
import com.api.aluimoveis.security.TokenService;
import com.api.aluimoveis.service.UserService;
import com.api.aluimoveis.service.impl.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.usertype.UserVersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Contém endpoints para gerenciamento de usuários")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TokenService tokenService;

    @PostMapping("create")
    @CrossOrigin
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com as informações fornecidas.")
    public ResponseEntity<String> save(@RequestBody @Valid UserDto userDto) {
        if (this.userService.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String hashPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(hashPassword);

        User savedUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("login")
    @CrossOrigin
    @Operation(summary = "Fazer login", description = "Faz login com as credenciais fornecidas.")
    public ResponseEntity<Token> login(
            @RequestBody LoginDto loginDto
    ) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(new Token(token));
    }

    @PutMapping("/update/{id}")
    @CrossOrigin
    @Operation(summary = "Atualizar um usuário", description = "Atualiza dados de um usuario especifico, com os dados fornecidos.")
    public ResponseEntity<String> updateUser(
            @PathVariable @Valid Long id,
            @RequestBody @Valid UserDto userDto
    ) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK + " : User updated successfully");
    }

    @GetMapping("{id}")
    @CrossOrigin
    @Operation(summary = "Obter um usuário por id", description = "Atualiza dados de um usuario especifico, com os dados fornecidos.")
    public ResponseEntity<Object> getById(@PathVariable @Valid Long id) {
        var user = this.userService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(toUserView(user.get()));
    }

    private UserViewDto toUserView(User user) {
        return new UserViewDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAccess()
        );
    }

}
