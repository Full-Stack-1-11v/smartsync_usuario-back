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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Controlador para gestionar usuarios")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    User user = new User();

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    // private static final Logger logger =
    // Logger.getLogger(UserController.class.getName());

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios.")
    })
    public ResponseEntity<CollectionModel<EntityModel<User>>> listar() {
        logger.info("Listando todos los usuarios");
        List<User> users = userService.findAll();
        List<EntityModel<User>> userModels = users.stream()
                .map(userModelAssembler::toModel)
                .toList();
        if (users.isEmpty()) {
            logger.warn("No se encontraron usuarios");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Usuarios encontrados: {}", users.size());
            return ResponseEntity.ok(CollectionModel.of(userModels,
                    linkTo(methodOn(UserController.class).listar()).withSelfRel()));
        }
    }

    // buscar usuario por id y devovlver un activo si es true o incativo si es false
    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar un usuario por ID", description = "Obtiene un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    public ResponseEntity<EntityModel<User>> buscarPorId(@PathVariable Integer id) {
        logger.info("Buscando usuario con ID: {}", id);
        try {

            User user = userService.userId(id);
            EntityModel<User> userModel = userModelAssembler.toModel(user);
            logger.info("Usuario encontrado: {}", user.getName());
            return ResponseEntity.ok(userModel);
        } catch (RuntimeException e) {
            logger.error("Usuario no encontrado con ID = {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    // obtener usuarios inactivos
    @GetMapping("/inactivos")
    @Operation(summary = "Obtener usuarios inactivos", description = "Devuelve una lista de usuarios que están inactivos (estado false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios inactivos obtenida correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios inactivos.")
    })
    public ResponseEntity<CollectionModel<EntityModel<User>>> obtenerInactivos() {
        logger.info("Obteniendo usuarios inactivos");
        List<User> inactivos = userService.findByEstadoFalse();
        List<EntityModel<User>> userModels = inactivos.stream()
                .map(userModelAssembler::toModel)
                .toList();
        if (inactivos.isEmpty()) {
            logger.warn("No se encontraron usuarios inactivos");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Usuarios inactivos encontrados: {}", inactivos.size());
            return ResponseEntity.ok(CollectionModel.of(userModels,
                    linkTo(methodOn(UserController.class).obtenerInactivos()).withSelfRel()));
        }
    }

    @GetMapping("/listar/dto")
    @Operation(summary = "Listar todos los usuarios como DTO", description = "Obtiene una lista de todos los usuarios registrados en formato DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios DTO obtenida correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios DTO."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> listarDto() {
        logger.info("Listando todos los usuarios como DTO");
        List<UserDto> userDtos = userService.findAllDto();
        List<EntityModel<UserDto>> userModelDtos = userDtos.stream()
                .map(userModelAssembler::toModelDto)
                .toList();
        if (userDtos.isEmpty()) {
            logger.warn("No se encontraron usuarios DTO");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Usuarios DTO encontrados: {}", userDtos.size());
            return ResponseEntity.ok(CollectionModel.of(userModelDtos,
                    linkTo(methodOn(UserController.class).listarDto()).withSelfRel()));
        }
    }

    @GetMapping("/{id}/dto")
    @Operation(summary = "Obtener usuario por ID como DTO", description = "Devuelve un usuario específico por su ID en formato DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario DTO encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario DTO no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<EntityModel<UserDto>> obtenerUsuarioDto(@PathVariable Integer id) {
        logger.info("Buscando usuario DTO con ID: {}", id);
        try {
            // Obtiene el usuario por id
            User user = userService.userId(id);
            // Convierte el usuario a DTO
            UserDto dto = userService.toDto(user);
            // Envuelve el DTO en un EntityModel con enlaces HATEOAS
            EntityModel<UserDto> userModelDto = userModelAssembler.toModelDto(dto);
            logger.info("Usuario DTO encontrado: {}", dto.getName());
            return ResponseEntity.ok(userModelDto);
        } catch (RuntimeException e) {
            logger.error("Usuario DTO no encontrado con ID = {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<EntityModel<User>> guardar(@RequestBody User user) {
        logger.info("Guardando nuevo usuario: {}", user.getName());
        User nuevoUser = userService.save(user);
        EntityModel<User> userModel = userModelAssembler.toModel(nuevoUser);
        // Aquí puedes agregar enlaces HATEOAS adicionales si es necesario
        logger.info("Usuario creado con ID: {}", nuevoUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @PutMapping("/{id}/actualizar")
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza los detalles de un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<EntityModel<User>> actualizar(@PathVariable Integer id, @RequestBody User user) {
        logger.info("Actualizando usuario con ID: {}", id);
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
            logger.info("Usuario actualizado: {}", usuario.getName());
            return ResponseEntity.ok(userModel);

        } catch (Exception e) {
            logger.error("Error al actualizar el usuario con ID = {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar un usuario por ID", description = "Elimina un usuario del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        logger.info("Eliminando usuario con ID: {}", id);
        try {
            userService.deleteById(id);
            logger.info("Usuario con ID {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario con ID = {}", id, e);
            return ResponseEntity.notFound().build();

        }

    }

    // eliminar usuario por estado
    @DeleteMapping("/{id}/eliminar/estado")
    @Operation(summary = "Eliminar un usuario por estado", description = "Elimina un usuario estableciendo su estado a false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado por estado correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> eliminarPorEstado(@PathVariable Integer id) {
        logger.info("Eliminando usuario con ID: {} estableciendo estado a false", id);
        try {
            userService.deleteByIdFalse(id);
            logger.info("Usuario con ID {} eliminado por estado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el usuario con ID = {} por estado", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // login
    // Este método recibe un objeto User con el email y la contraseña,
    // y devuelve un mensaje de éxito o error
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión con su email y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso."),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<String> login(@RequestBody User user) {
        logger.info("Intentando iniciar sesión para el usuario: {}", user.getEmail());
        try {
            boolean exito = userService.login(user.getEmail(), user.getPassword());
            if (exito) {
                logger.info("Inicio de sesión exitoso para el usuario: {}", user.getEmail());
                return ResponseEntity.ok("inicio sesion exitoso");
            } else {
                logger.warn("Credenciales incorrectas para el usuario: {}", user.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("credenciales incorrectas");
            }
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            logger.error("Error al iniciar sesión para el usuario: {}", user.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // eliminar todos los inactivos
    @DeleteMapping("/eliminar/inactivos")
    @Operation(summary = "Eliminar usuarios inactivos", description = "Elimina todos los usuarios que están inactivos (estado false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuarios inactivos eliminados correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> eliminarInactivos() {
        logger.info("Eliminando todos los usuarios inactivos");
        try {
            userService.deleteByEstadoFalse();
            logger.info("Usuarios inactivos eliminados correctamente");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar usuarios inactivos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @GetMapping("/test-delete")
    @Operation(summary = "Test Delete", description = "Prueba de endpoint para verificar la eliminación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El servidor responde correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")

    })
    public ResponseEntity<String> testDelete() {
        logger.info("Probando endpoint de eliminación");
        return ResponseEntity.ok("El servidor responde correctamente");
    }

    public Class<?> buscarPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
}
