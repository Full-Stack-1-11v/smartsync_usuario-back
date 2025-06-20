package cl.ecomarket.user.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.assembles.RolModelAssembler;
import cl.ecomarket.user.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/rol")
@Tag(name = "Rol", description = "Controlador para gestionar roles de usuario")
public class RolController {

        private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssembler rolModelAssembler;

    Rol rol = new Rol();

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los roles",description = "Obtiene una lista de todos los roles registrados")
    @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",description = "Lista de roles obtenida correctamente.",
        content = @Content( mediaType = "application/json",
            schema = @Schema(implementation = cl.ecomarket.user.model.Rol.class))),
    @ApiResponse(responseCode = "204",description = "No se encontraron roles.")})
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> listarRoles() {
        logger.info("Listando todos los roles");
        List<Rol> roles = rolService.listaList();
        List<EntityModel<Rol>> rolModels = roles.stream()
                .map(rolModelAssembler::toModelRol)
                .toList();
        if (roles.isEmpty()) {
            logger.warn("No se encontraron roles");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Roles encontrados: {}", roles.size());
            return ResponseEntity.ok(CollectionModel.of(
                    rolModels,
                    linkTo(methodOn(RolController.class).listarRoles()).withSelfRel()));
        }
    }

    @GetMapping("/{id}/buscar")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado.",
             content = @Content(mediaType = "application/json", 
             schema = @Schema(implementation = cl.ecomarket.user.model.Rol.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado.") })
    public ResponseEntity<EntityModel<Rol>> buscarRolPorId(@PathVariable Integer id) {
        logger.info("Buscando rol con ID: {}", id);
        try {
            Rol rol = rolService.findById(id);
            EntityModel<Rol> rolModel = rolModelAssembler.toModelRol(rol);
            logger.info("Rol encontrado: {}", id);
            return ResponseEntity.ok(rolModel);
        } catch (RuntimeException e) {
            logger.error("Error al buscar rol con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo rol", description = "Crea un nuevo rol en el sistema")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",description = "Rol creado correctamente.",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = cl.ecomarket.user.model.Rol.class))),
        @ApiResponse(
            responseCode = "400",description = "Solicitud incorrecta."),
        @ApiResponse(
            responseCode = "500",description = "Error interno del servidor.")})
    public ResponseEntity<EntityModel<Rol>> guardar(@RequestBody Rol rol) {
        logger.info("Guardando nuevo rol: {}", rol.getNombreRol());
        try {
            Rol nuevoRol = rolService.save(rol);
            EntityModel<Rol> rolModel = rolModelAssembler.toModelRol(nuevoRol);
            logger.info("Rol creado con ID: {}", nuevoRol.getId_rol());
            return ResponseEntity.status(HttpStatus.CREATED).body(rolModel);
        } catch (RuntimeException e) {
            logger.error("Error al guardar rol: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
