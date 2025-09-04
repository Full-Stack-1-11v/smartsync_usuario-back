package cl.ecomarket.user.assembles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import cl.ecomarket.user.controller.RolController;
import cl.ecomarket.user.model.Rol;
import io.micrometer.common.lang.NonNull;
/**
 * Assembler para convertir objetos Rol en EntityModel con enlaces HATEOAS.
 */
@Component
public class RolModelAssembler {
    /**
     * Convierte un Rol en un EntityModel con enlaces HATEOAS a operaciones relacionadas.
     * @param rol Rol a convertir.
     * @return EntityModel con enlaces HATEOAS.
     */
    @NonNull
public EntityModel<Rol> toModelRol(@NonNull Rol rol) {
    return EntityModel.of(
            rol,
            linkTo(methodOn(RolController.class).buscarRolPorId(rol.getId_rol())).withSelfRel(),
            linkTo(methodOn(RolController.class).listarRoles()).withRel("roles"),
            linkTo(methodOn(RolController.class).guardar(null)).withRel("guardar"));
}
}
