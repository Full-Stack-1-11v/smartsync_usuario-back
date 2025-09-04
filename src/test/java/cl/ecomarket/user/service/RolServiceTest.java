package cl.ecomarket.user.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.repository.RolRepository;

@SpringBootTest
@ActiveProfiles("test")
public class RolServiceTest {
    @Autowired
    private RolService rolService;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private UserService userService;

    // test listar roles
    @Test
    void testListaRoles() {
        Rol rol1 = new Rol(1,"administrador");
        Rol rol2 = new Rol(2,"cliente");
        List<Rol> roles = List.of(rol1, rol2);

        // simular que el repositorio devuelve los roles
        when(rolRepository.findAll()).thenReturn(roles);

        // when 
        List<Rol> result = rolService.listaList();

        // then
        assertEquals(2, result.size());
        assertEquals(roles, result);
        verify(rolRepository).findAll();

    }

    // test buscar por id rol 
    @Test
    public void TestbuscaridRol() {
        //given 
        int rolid= 1;
        Rol rol = new Rol(rolid, "administrador");
        // silumar qie el repositorio devuelve el rol
        when(rolRepository.findById(rolid)).thenReturn(java.util.Optional.of(rol));
        Rol foundRol = rolService.findById(rolid);
        // then
        assertEquals(rolid, foundRol.getId_rol());

    }
   

    // test crear rol
    @Test
    void testsaveRol() {
        // given
        Rol rol = new Rol(1, "cliente");
        when(rolRepository.save(rol)).thenReturn(rol);
        // when 
        Rol saveRol = rolService.save(rol);
        // then
        assertEquals(rol, saveRol);
        verify(rolRepository).save(rol);

    }
    @Test
    void testFindById_RolNoEncontrado () {
        int rolid = 1;
        when(rolRepository.findById(rolid)).thenReturn(java.util.Optional.empty());
        // when 
        RuntimeException excepcion = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            rolService.findById(rolid);
        });
        assertEquals("Rol no encontrado con ID: " + rolid, excepcion.getMessage());


    }



    // agregar rol
    @Test
    void testSaveRol () {
        Rol rol = new Rol(1, "vendedor");
        when(rolRepository.save(rol)).thenReturn(rol);

        // when
        Rol saveRol = rolService.save(rol);
        // then
        assertEquals(rol,saveRol);
        verify(rolRepository).save(rol);

    }

    // eliminar rol por id
    @Test
    void testDeleteRol() {
        // given 
        int rolid = 1;
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(rolid, "cliente"));
        // simular que existe el rol
        when(rolRepository.existsById(rolid)).thenReturn(true);
        // when
        rolService.deleteById(rolid);
        // then
        // verificar que el repositorio llamo con el id correcto
        verify(rolRepository).deleteById(rolid);


    }
    
}
