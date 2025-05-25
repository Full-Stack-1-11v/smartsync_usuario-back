package cl.ecomarket.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.ecomarket.user.dto.UserDto;
import cl.ecomarket.user.model.User;

@FeignClient(name = "user-api", url = "https://smartsync-usuario-back-pruebas.onrender.com")
public interface UserFeingClient {


    @GetMapping("/api/v1/user/listar")
    List<UserDto> listarUser();

    @PostMapping("/api/v1/user/guardar")
    UserDto guardarUser(@RequestBody UserDto userDto);

    @PutMapping("/api/v1/user/{id}/actualizar")
    UserDto actualizarUser(@PathVariable("id") Integer id, @RequestBody UserDto userDto);

    @DeleteMapping("/api/v1/user/{id}/eliminar")
    void eliminarUser(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/user/{id}/dto")
    UserDto obtenerUsuarioDto(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/user/inactivos")
    List<UserDto> obtenerInactivos();

    @DeleteMapping("/api/v1/user/eliminar/inactivos")
    void eliminarInactivos();

    @PostMapping("/api/v1/user/login")
    String login(@RequestBody User user);


    
}
