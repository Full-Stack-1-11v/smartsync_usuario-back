package cl.ecomarket.user.controller;

import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.service.RolService;
import cl.ecomarket.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RolController.class)
public class RolControllerTest {

    @MockBean
    private RolService rolService;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private UserService userService;

    @Test
    void testListarRolesNoContent() throws Exception {
        Mockito.when(rolService.listaList()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/rol/listar"))
                .andExpect(status().isNoContent());
    }

    
    @Test
    void testListarRolesOk() throws Exception {
    Rol rol = new Rol();
    rol.setId_rol(1);
    rol.setNombreRol("ADMIN");
    Mockito.when(rolService.listaList()).thenReturn(Collections.singletonList(rol));
    mockMvc.perform(get("/api/v1/rol/listar"))
            .andExpect(status().isOk());
            
    }
    // aca se evalua los el try exitoso 
    @Test
    void testBuscarRolPorIdOk() throws Exception {
        Rol rol = new Rol ();
        rol.setId_rol(1);
        rol.setNombreRol("ADMIN");
        Mockito.when(rolService.findById(1)).thenReturn(rol);

        mockMvc.perform(get("/api/v1/rol/1/buscar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_rol").value(1))
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
                
    }

    // aca se simula el try catch con una excepcion
    @Test
    void testBuscarRolPorIdNotFound() throws Exception {
        Mockito.when(rolService.findById(1)).thenThrow(new RuntimeException("Rol not found"));

        mockMvc.perform(get("/api/v1/rol/1/buscar"))
                .andExpect(status().isNotFound());  


    }

    // aca se evalua el metodo guardar rol correctamente con http status 201
    @Test
    void testGuardarRol() throws Exception {
        Rol rol = new Rol();
        rol.setId_rol(1);
        rol.setNombreRol("ADMIN");
        Mockito.when(rolService.save(Mockito.any(Rol.class))).thenReturn(rol);

        mockMvc.perform(post("/api/v1/rol/guardar")
        .contentType("application/json")
        .content("{\"id_rol\": 1, \"nombreRol\": \"ADMIN\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id_rol").value(1))
        .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    // aca se evalua el metodo guardar rol con un error de validacion se espera un http status 500
    @Test
    void testGuardarRolValidationError() throws Exception {
         Mockito.when(rolService.save(Mockito.any(Rol.class))).thenThrow(new RuntimeException("Error al guardar"));

        mockMvc.perform(post("/api/v1/rol/guardar")
    .contentType("application/json")
    .content("{\"id_rol\": 1, \"nombreRol\": \"ADMIN\"}"));
    }
}