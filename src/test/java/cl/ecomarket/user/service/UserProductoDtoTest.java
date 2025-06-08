package cl.ecomarket.user.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserProductoDtoTest {

    @Autowired
    private UserProductoDtoService userProductoDtoService;

    // lista de productos dto
    @Test
    void TestLIstaDto () {
        assertNull(userProductoDtoService.listaList());

    }
    
    // buscar por id
    @Test
    void TestFindById() {
        assertNull(userProductoDtoService.findById(1));
    }
    // agregar producto
    @Test
    void TestSave() {
        assertNull(userProductoDtoService.save(null));
    }
    // eliminar producto
    @Test
    void TestDeleteById() {
        userProductoDtoService.deleteById(1);
        assertNull(userProductoDtoService.findById(1));
    }
}
