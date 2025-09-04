package cl.ecomarket.user.assembles;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

import cl.ecomarket.user.controller.UserController;
import cl.ecomarket.user.dto.UserDto;

import cl.ecomarket.user.model.User;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
/**
 * Assembler para convertir objetos User y UserDto en EntityModel con enlaces HATEOAS.
 */
@Component
public class UserModelAssembler {
    /**
     * Convierte un User en un EntityModel con enlaces HATEOAS a operaciones relacionadas.
     * @param user Usuario a convertir.
     * @return EntityModel con enlaces HATEOAS.
     *linkTo es un método estático que genera enlaces HATEOAS para las operaciones del controlador.
     */
    @NonNull
    public EntityModel<User> toModel(@NonNull User user) {

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).buscarPorId(user.getId())).withRel("buscarPorId"),
                linkTo(methodOn(UserController.class).guardar(user)).withRel("guardar"),
                linkTo(methodOn(UserController.class).listar()).withRel("users"),
                linkTo(methodOn(UserController.class).actualizar(user.getId(), user)).withRel("actualizar"),
                linkTo(methodOn(UserController.class).eliminar(user.getId())).withRel("eliminar"),
                linkTo(methodOn(UserController.class).obtenerInactivos()).withRel("inactivos")
        );
    }
/**
     * Convierte un UserDto en un EntityModel con enlaces HATEOAS a operaciones relacionadas.
     * @param userDto DTO de usuario a convertir.
     * @return EntityModel con enlaces HATEOAS.
     */
    @NonNull
    public EntityModel<UserDto> toModelDto(@NonNull UserDto userDto) {

        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).buscarPorId(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).listar()).withRel("users")
        );
    }


}