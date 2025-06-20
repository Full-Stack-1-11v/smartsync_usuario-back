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
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/rol")
@Tag(name = "Rol", description = "Controlador para gestionar roles de usuario")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssembler rolModelAssembler;

    Rol rol = new Rol();

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los roles", description = "Obtiene una lista de todos los roles registrados")
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> listarRoles() {
        List<Rol> roles = rolService.listaList();
        List<EntityModel<Rol>> rolModels = roles.stream()
                .map(rolModelAssembler::toModelRol)
                .toList();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(CollectionModel.of(
                    rolModels,
                    linkTo(methodOn(RolController.class).listarRoles()).withSelfRel()));
        }
    }

    @GetMapping("/{id}/buscar")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico por su ID")
    public ResponseEntity<EntityModel<Rol>> buscarRolPorId(@PathVariable Integer id) {
        try {
            Rol rol = rolService.findById(id);
            EntityModel<Rol> rolModel = rolModelAssembler.toModelRol(rol);
            return ResponseEntity.ok(rolModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo rol", description = "Crea un nuevo rol en el sistema")
    public ResponseEntity<EntityModel<Rol>> guardar(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.save(rol);
            EntityModel<Rol> rolModel = rolModelAssembler.toModelRol(nuevoRol);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
