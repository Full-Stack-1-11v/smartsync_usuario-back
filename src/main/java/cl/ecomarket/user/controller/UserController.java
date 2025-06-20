package cl.ecomarket.user.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
//import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import cl.ecomarket.user.assembles.UserModelAssembler;
import cl.ecomarket.user.dto.UserDto;
import cl.ecomarket.user.model.User;

import cl.ecomarket.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Controlador para gestionar usuarios")
public class UserController {

    User user = new User();

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    //private static final Logger  logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    public ResponseEntity<CollectionModel<EntityModel<User>>> listar() {
        List<User> users = userService.findAll();
        List<EntityModel<User>> userModels = users.stream()
                .map(userModelAssembler::toModel)
                .toList();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(CollectionModel.of(userModels,
            linkTo(methodOn(UserController.class).listar()).withSelfRel()));
        }
    }

    // buscar usuario por id y devovlver un activo si es true o incativo si es false
    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar un usuario por ID", description = "Obtiene un usuario específico por su ID")
    public ResponseEntity<EntityModel<User>> buscarPorId(@PathVariable Integer id)
     {
        try {
            
            User user = userService.userId(id);
            EntityModel<User> userModel = userModelAssembler.toModel(user);
            return ResponseEntity.ok(userModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
   
 // obtener usuarios inactivos
    @GetMapping("/inactivos")
    @Operation(summary = "Obtener usuarios inactivos", description = "Devuelve una lista de usuarios que están inactivos (estado false)")   
    public ResponseEntity<CollectionModel<EntityModel<User>>> obtenerInactivos() {
        List<User> inactivos = userService.findByEstadoFalse();
        List<EntityModel<User>> userModels = inactivos.stream()
                .map(userModelAssembler::toModel)
                .toList();  
        if (inactivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(CollectionModel.of(userModels,
            linkTo(methodOn(UserController.class).obtenerInactivos()).withSelfRel()));
        }
    }

    @GetMapping("/listar/dto")
    @Operation(summary = "Listar todos los usuarios como DTO", description = "Obtiene una lista de todos los usuarios registrados en formato DTO")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> listarDto() {
        List<UserDto> userDtos = userService.findAllDto();
        List<EntityModel<UserDto>> userModelDtos = userDtos.stream()
        .map(userModelAssembler::toModelDto)
        .toList();
        if (userDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(CollectionModel.of(userModelDtos,
            linkTo(methodOn(UserController.class).listarDto()).withSelfRel()));
        }
    }

    @GetMapping("/{id}/dto")
@Operation(summary = "Obtener usuario por ID como DTO", description = "Devuelve un usuario específico por su ID en formato DTO")
public ResponseEntity<EntityModel<UserDto>> obtenerUsuarioDto(@PathVariable Integer id) {
    try {
        // Obtiene el usuario por id
        User user = userService.userId(id);
        // Convierte el usuario a DTO
        UserDto dto = userService.toDto(user);
        // Envuelve el DTO en un EntityModel con enlaces HATEOAS
        EntityModel<UserDto> userModelDto = userModelAssembler.toModelDto(dto);
        return ResponseEntity.ok(userModelDto);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}


    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<EntityModel<User>> guardar(@RequestBody User user) {
        User nuevoUser = userService.save(user);
        EntityModel<User> userModel = userModelAssembler.toModel(nuevoUser);
        // Aquí puedes agregar enlaces HATEOAS adicionales si es necesario
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }


    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza los detalles de un usuario por su ID")
    public ResponseEntity<EntityModel<User>> actualizar(@PathVariable Integer id, @RequestBody User user) {
        try {

            User usuario = userService.userId(id);
            usuario.setId(id);
            usuario.setName(user.getName());
            usuario.setEmail(user.getEmail());
            usuario.setEstado(user.isEstado());
            usuario.setPassword(user.getPassword());
            usuario.setRol(user.getRol());

            userService.save(usuario);
            EntityModel<User> userModel = userModelAssembler.toModel(usuario);
            return ResponseEntity.ok(userModel);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar un usuario por ID", description = "Elimina un usuario del sistema por su ID")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }

    }

    

    // eliminar usuario por estado
    @DeleteMapping("/{id}/eliminar/estado")
    @Operation(summary = "Eliminar un usuario por estado", description = "Elimina un usuario estableciendo su estado a false")
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
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión con su email y contraseña")   
    public ResponseEntity<String> login(@RequestBody User user) {
    try {
        boolean exito = userService.login(user.getEmail(), user.getPassword());
        if (exito) {
            return ResponseEntity.ok("inicio sesion exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("credenciales incorrectas");
        }
    } catch (Exception e) {
        // Log the error for debugging
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }



    // eliminar todos los inactivos
    @DeleteMapping("/eliminar/inactivos")
    @Operation(summary = "Eliminar usuarios inactivos", description = "Elimina todos los usuarios que están inactivos (estado false)")
    public ResponseEntity<?> eliminarInactivos() {
        try {
            userService.deleteByEstadoFalse();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    @GetMapping("/test-delete")
    @Operation(summary = "Test Delete", description = "Prueba de endpoint para verificar la eliminación")
        public ResponseEntity<String> testDelete() {
            return ResponseEntity.ok("El servidor responde correctamente");
        }

    public Class<?> buscarPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
}
   

