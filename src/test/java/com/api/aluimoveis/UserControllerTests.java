package com.api.aluimoveis;

import com.api.aluimoveis.controller.UserController;
import com.api.aluimoveis.dto.UserDto;
import com.api.aluimoveis.entity.User;
import com.api.aluimoveis.entity.UserRole;
import com.api.aluimoveis.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)  // Extende a funcionalidade do JUnit 5 com o suporte do Mockito
public class UserControllerTests {

    @InjectMocks
    private UserController userController;  // Injeta mocks no UserController

    @Mock
    private UserService userService;  // Cria um mock do UserService

    private ObjectMapper objectMapper;  // Para converter objetos Java em JSON e vice-versa
    private UserDto userDto;  // Representa um DTO de usuário
    private MockMvc mockMvc;  // Simula requisições HTTP para o controlador

    @BeforeEach
    public void setup() {
        // Inicializa o UserDto com valores de teste
        userDto = new UserDto("irineu", "irineu@gmail.com", "qwe123", UserRole.OWNER);

        // Inicializa o ObjectMapper
        objectMapper = new ObjectMapper();

        // Configura o MockMvc com o UserController
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSaveUser_Success() throws Exception {
        // Configura o mock para retornar null quando o e-mail não for encontrado
        when(userService.findByEmail(userDto.getEmail())).thenReturn(null);
        // Configura o mock para retornar um novo usuário quando salvar
        when(userService.save(any(UserDto.class))).thenReturn(new User());

        // Realiza uma requisição POST para /users/create com o JSON do userDto
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())  // Verifica se o status é 201 Created
                .andExpect(content().string("User created successfully"));  // Verifica se a resposta contém a string esperada
    }

    @Test
    public void testSaveUser_EmailAlreadyExists() throws Exception {
        // Configura o mock para retornar um usuário existente quando o e-mail já estiver registrado
        when(userService.findByEmail(userDto.getEmail())).thenReturn(new User());

        // Realiza uma requisição POST para /users/create com o JSON do userDto
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());  // Verifica se o status é 400 Bad Request
    }
}
