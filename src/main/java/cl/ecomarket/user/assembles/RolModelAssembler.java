package cl.ecomarket.user.assembles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import cl.ecomarket.user.controller.RolController;
import cl.ecomarket.user.model.Rol;
import io.micrometer.common.lang.NonNull;

@Component
public class RolModelAssembler {
    @NonNull
public EntityModel<Rol> toModelRol(@NonNull Rol rol) {
    return EntityModel.of(
            rol,
            linkTo(methodOn(RolController.class).buscarRolPorId(rol.getId_rol())).withSelfRel(),
            linkTo(methodOn(RolController.class).listarRoles()).withRel("roles"),
            linkTo(methodOn(RolController.class).guardar(null)).withRel("guardar"));
}
}
