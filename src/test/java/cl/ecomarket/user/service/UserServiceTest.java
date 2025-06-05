package cl.ecomarket.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import cl.ecomarket.user.model.User;
import cl.ecomarket.user.repository.UserRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    
}
