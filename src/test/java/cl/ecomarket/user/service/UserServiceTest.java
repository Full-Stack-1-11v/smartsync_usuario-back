package cl.ecomarket.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import cl.ecomarket.user.dto.UserDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.repository.UserRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // given 

        List<User> users = new ArrayList<>();
        users.add(new User(1,"juan","@juan","1234", true, null));
        // when
        when(userRepository.findAll()).thenReturn(users);
        // then

        List<User> foundUsers = userService.findAll();
        assertEquals(1, foundUsers.size());
    }


    // eliminar usuario por id
    @Test
    public void TestDeletUser() {

        // given 
        int userId = 1;
        List<User> users = new ArrayList<>();
        users.add(new User(userId,"juan","@juan","1234",true,null));
        // simular que existe el usuario
        when(userRepository.existsById(userId)).thenReturn(true);
        // when
        userService.deleteById(userId);

        // then 
        // verificar que el repositorio llamo con el id correcto 
        verify(userRepository).deleteById(userId);
    }   


    //buscar por id
    @Test
    public void TestUserIDfound () {
        // given
        int userid=1;
        User user = new User(userid,"juan","@juan","1234",true,null);

        // when
        when(userRepository.findById(userid)).thenReturn(java.util.Optional.of(user));

        User found = userService.userId(userid);

        // then 
        assertEquals(user, found);

    }


    // buscar por id no encontrado
    @Test
    public void TestUserIDNotFound() {
        // given 
        int userid = 1;
        when(userRepository.findById(userid)).thenReturn(java.util.Optional.empty());
        // then 
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            userService.userId(userid);
        });
        assertEquals("Usuario no encontrado con ID: " + userid, exception.getMessage());


    }

    // testing dto
    @Test
    void listarDto_ReturnsOkWithList() {
        // given
        User user = new User(1, "juan", "@juan", "1234", true, null);
        when(userRepository.findAll()).thenReturn(List.of(user));

        // when
        List<UserDto> result = userService.findAllDto();

        // then
        assertEquals(1, result.size());
    }

    // agregar user 
    @Test
    void testSaveUser() {
        // given 
        User user = new User(1, "juan", "@juan", "1234", true, null);
        when(userRepository.save(user)).thenReturn(user);
        // when 
        User savedUser = userService.save(user);

        // then 
        assertEquals(user, savedUser);
        verify(userRepository).save(user);

    }



    // delete user by estado false
    @Test
    void testDeleteByEstadoFalse() {
        // GIVEN 
        User user1 = new User(1, "juan", "@juan", "1234", false, null);
        List<User> inactivos = List.of(user1);
        when(userRepository.findByEstadoFalse()).thenReturn(inactivos);

        // when 
        userService.deleteByEstadoFalse();

        // then 
        verify(userRepository).findByEstadoFalse();
        verify(userRepository).deleteAll(inactivos);
    }

    // findby estado false
    @Test
    void testFindByEstadoFalse() {
        // given 
        User user1 = new User(1, "juan", "@juan", "1234", false, null);
        User user2 = new User(2, "maria", "@maria", "5678", false, null);
        List<User> inactivos = List.of(user1, user2);
        when(userRepository.findByEstadoFalse()).thenReturn(inactivos);

        // when 
        List<User> result = userService.findByEstadoFalse();

        // then 
        assertEquals(2, result.size());
        assertEquals(inactivos, result);
        verify(userRepository).findByEstadoFalse();
    }

    // test login
    @Test
    void testLoginSuccess() {
        // given
        String email = "juan@mail.com";
        String password = "1234";
        User user = new User(1, "juan", email, password, true, null);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // when
        boolean result = userService.login(email, password);

        // then
        assertTrue(result);
        }
    
   // testLoginWrongPassword
   @Test
   void testLoginWrongPassword() {
    // given   
    String email = "juan@mail.com";
    String password = "1234";
    User user = new User(1, "juan", email, password, true, null);
    when(userRepository.findByEmail(email)).thenReturn(user);

    // when 
    boolean result = userService.login(email, "wrongpassword");
    // then
    assertFalse(result);

   }

    // testLoginUserNotFound
    @Test
    void testLoginUserNotFound() {
        // given
        String email = "noexiste@mail.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // when
        boolean result = userService.login(email,"any");
        // then
        assertFalse(result);
    }


    @Test
    void testDeleteByIdFalse_WhenUserIsInactive() {
        // given
        int userId = 1;
        User user = new User(userId, "juan", "@juan", "1234", false, null); // estado = false
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // when
        userService.deleteByIdFalse(userId);

        // then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testDeleteByIdFalse_WhenUserIsActive() {
        // given
        int userId = 2;
        User user = new User(userId, "ana", "@ana", "abcd", true, null); // estado = true
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // then
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            userService.deleteByIdFalse(userId);
        });
        assertEquals("No se puede eliminar el usuario porque está habilitado", exception.getMessage());
        verify(userRepository, never()).deleteById(userId);
    }


    @Test
    void testDeleteByIdFalse_UserNotFound() {
        // given
        int userId = 3;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // then
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            userService.deleteByIdFalse(userId);
        });
        assertEquals("Usuario no encontrado con ID: " + userId, exception.getMessage());
        verify(userRepository, never()).deleteById(userId);
}
}
