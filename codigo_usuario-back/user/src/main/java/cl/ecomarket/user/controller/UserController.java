package cl.ecomarket.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listar")
    public ResponseEntity <List<User>> listar() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<User> guardar(@RequestBody User user) {
        User nuevoUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUser);
    }


    @PutMapping("{id}/actualizar")
    public ResponseEntity<User> actualizar(@PathVariable Integer id, @RequestBody User user) {
        try {

            User usuario = userService.findById(id);
            usuario.setId(id);
            usuario.setName(user.getName());
            usuario.setEmail(user.getEmail());
            usuario.setEstado(user.isEstado());

            userService.save(usuario);
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }

    }

    // buscar usuario por id y devovlver un activo si es true o incativo si es false
    @GetMapping("/buscar/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // eliminar usuario por estado
    @DeleteMapping("{id}/eliminar/estado")
    public ResponseEntity<?> eliminarPorEstado(@PathVariable Integer id) {
        try {
            userService.deleteByIdFalse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // login
    // Este método recibe un objeto User con el email y la contraseña, 
    //y devuelve un mensaje de éxito o error
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean exito = userService.login(user.getEmail(), user.getPassword());
        if(exito) {
            return ResponseEntity.ok("inicio sesion exitoso");
        } else {
            return ResponseEntity.status(401).body("credenciales incorrectas");
        }
        
    }

    // obtener usuarios inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<List<User>> obtenerInactivos() {
        List<User> inactivos = userService.findByEstadoFalse();
        if (inactivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(inactivos);
        }
    }
    

}
   

