package cl.ecomarket.user.controller;

import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.dto.UserDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.RolService;
import cl.ecomarket.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @MockBean
    private RolService rolService;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private UserService userService;

    // listar usuarios
    @Test
    void testListarUsuariosNoContent() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/user/listar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarUsuariosOk() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Test");
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        mockMvc.perform(get("/api/v1/user/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // listar usuarios dto
    @Test
    void testListarUsuariosDtoNoContent() throws Exception {
        Mockito.when(userService.findAllDto()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/user/listar/dto"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarUsuariosDtoOk() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setName("Test");
        Mockito.when(userService.findAllDto()).thenReturn(List.of(dto));
        mockMvc.perform(get("/api/v1/user/listar/dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // guardar usuario
    @Test
    void testGuardarUsuario() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Test");
        Mockito.when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    // actualizar usuario
    @Test
    void testActualizarUsuarioOk() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Test");
        Mockito.when(userService.userId(1)).thenReturn(user);
        Mockito.when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/user/1/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Nuevo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizarUsuarioNotFound() throws Exception {
        Mockito.when(userService.userId(1)).thenThrow(new RuntimeException("No encontrado"));
        mockMvc.perform(put("/api/v1/user/1/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Nuevo\"}"))
                .andExpect(status().isNotFound());
    }

    // eliminar usuario
    @Test
    void testEliminarUsuarioOk() throws Exception {
        Mockito.doNothing().when(userService).deleteById(1);
        mockMvc.perform(delete("/api/v1/user/1/eliminar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarUsuarioNotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("No encontrado")).when(userService).deleteById(1);
        mockMvc.perform(delete("/api/v1/user/1/eliminar"))
                .andExpect(status().isNotFound());
    }

    // buscar usuario por id
    @Test
    void testBuscarPorIdOk() throws Exception {
        User user = new User();
        user.setId(1);
        Mockito.when(userService.userId(1)).thenReturn(user);
        mockMvc.perform(get("/api/v1/user/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testBuscarPorIdNotFound() throws Exception {
        Mockito.when(userService.userId(1)).thenThrow(new RuntimeException("No encontrado"));
        mockMvc.perform(get("/api/v1/user/buscar/1"))
                .andExpect(status().isNotFound());
    }

    // eliminar usuario por estado
    @Test
    void testEliminarPorEstadoOk() throws Exception {
        Mockito.doNothing().when(userService).deleteByIdFalse(1);
        mockMvc.perform(delete("/api/v1/user/1/eliminar/estado"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarPorEstadoBadRequest() throws Exception {
        Mockito.doThrow(new RuntimeException("Error")).when(userService).deleteByIdFalse(1);
        mockMvc.perform(delete("/api/v1/user/1/eliminar/estado"))
                .andExpect(status().isBadRequest());
    }

    // login
    @Test
    void testLoginOk() throws Exception {
        Mockito.when(userService.login(eq("test@mail.com"), eq("1234"))).thenReturn(true);
        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@mail.com\",\"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("inicio sesion exitoso"));
    }

    @Test
    void testLoginUnauthorized() throws Exception {
        Mockito.when(userService.login(eq("test@mail.com"), eq("1234"))).thenReturn(false);
        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@mail.com\",\"password\":\"1234\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("credenciales incorrectas"));
    }

    @Test
    void testLoginError() throws Exception {
        Mockito.when(userService.login(any(), any())).thenThrow(new RuntimeException("Error"));
        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@mail.com\",\"password\":\"1234\"}"))
                .andExpect(status().isInternalServerError());
    }

    // obtener usuarios inactivos
    @Test
    void testObtenerInactivosNoContent() throws Exception {
        Mockito.when(userService.findByEstadoFalse()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/user/inactivos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerInactivosOk() throws Exception {
        User user = new User();
        user.setId(1);
        Mockito.when(userService.findByEstadoFalse()).thenReturn(List.of(user));
        mockMvc.perform(get("/api/v1/user/inactivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // obtener usuario dto por id
    @Test
    void testObtenerUsuarioDtoOk() throws Exception {
        User user = new User();
        user.setId(1);
        UserDto dto = new UserDto();
        dto.setId(1);
        Mockito.when(userService.userId(1)).thenReturn(user);
        Mockito.when(userService.toDto(user)).thenReturn(dto);
        mockMvc.perform(get("/api/v1/user/1/dto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testObtenerUsuarioDtoNotFound() throws Exception {
        Mockito.when(userService.userId(1)).thenThrow(new RuntimeException("No encontrado"));
        mockMvc.perform(get("/api/v1/user/1/dto"))
                .andExpect(status().isNotFound());
    }

    // eliminar todos los inactivos
    @Test
    void testEliminarInactivosOk() throws Exception {
        Mockito.doNothing().when(userService).deleteByEstadoFalse();
        mockMvc.perform(delete("/api/v1/user/eliminar/inactivos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarInactivosError() throws Exception {
        Mockito.doThrow(new RuntimeException("Error")).when(userService).deleteByEstadoFalse();
        mockMvc.perform(delete("/api/v1/user/eliminar/inactivos"))
                .andExpect(status().isInternalServerError());
    }

    // test-delete endpoint
    @Test
    void testTestDelete() throws Exception {
        mockMvc.perform(get("/api/v1/user/test-delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("El servidor responde correctamente"));
    }
}