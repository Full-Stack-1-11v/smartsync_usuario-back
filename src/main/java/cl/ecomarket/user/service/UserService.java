package cl.ecomarket.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.user.dto.UserDto;
import cl.ecomarket.user.model.User;

import cl.ecomarket.user.repository.UserRepository;
import jakarta.transaction.Transactional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service para manejar la lógica de negocio relacionada con los usuarios.
 * * Este servicio proporciona métodos para buscar, guardar, eliminar y autenticar usuarios.
 * **/

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

     /**
     * Obtiene la lista de todos los usuarios.
     * @return Lista de usuarios.
     */
    public List<User> findAll(){
        logger.info("UserService Listando todos los usuarios");
        return userRepository.findAll();
    }


    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario.
     * @return Usuario encontrado.
     * @throws RuntimeException si no se encuentra el usuario.
     */
    public User userId(Integer id){
        logger.info("UserService Buscando usuario con ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Guarda un usuario en la base de datos.
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    public User save(User user){
        logger.info("UserService Guardando usuario: {}", user.getName());
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     */
    public void deleteById(Integer id){ 
        logger.info("UserService Eliminando usuario con ID: {}", id);
        userRepository.deleteById(id);
    }
    
     /**
     * Elimina un usuario por su ID solo si su estado es false (inactivo).
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si el usuario está habilitado o no existe.
     */
    public void deleteByIdFalse(Integer id) {
        logger.info("UserService Eliminando usuario por estado con ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        if (!user.isEstado()) {
            logger.info("UserService Eliminando usuario inactivo con ID: {}", id);
            userRepository.deleteById(id);
        } else {
            logger.warn("UserService No se puede eliminar el usuario con ID: {} porque está habilitado", id);
            throw new RuntimeException("No se puede eliminar el usuario porque está habilitado");
            }

        }

    /**
     * Realiza el login de un usuario verificando email y contraseña.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean login(String email, String password) {
        logger.info("UserService Intentando iniciar sesión para el usuario con email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("UserService Usuario no encontrado con email: {}", email);
            return false;
        }
        logger.info("UserService Usuario encontrado, verificando contraseña");
        return user.getPassword().equals(password);
        }

    /**
     * Obtiene la lista de usuarios inactivos (estado false).
     * @return Lista de usuarios inactivos.
     */
    public List<User> findByEstadoFalse() {
        logger.info("UserService Listando usuarios inactivos");
        return userRepository.findByEstadoFalse();
    }


    /**
     * Elimina todos los usuarios inactivos (estado false).
     */
    public void deleteByEstadoFalse() {
        logger.info("UserService Eliminando todos los usuarios inactivos");
        List<User> inactivos = userRepository.findByEstadoFalse();
        userRepository.deleteAll(inactivos);
    }

    /**
     * Convierte un objeto User a UserDto.
     * @param user Usuario a convertir.
     * @return UserDto resultante.
     */
     public UserDto toDto(User user) {
        logger.info("UserService Convirtiendo User a UserDto para el usuario con ID: {}", user.getId());
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setEstado(user.isEstado());
        logger.info("UserService User convertido a UserDto: {}", dto);
        return dto;
    }


     /**
     * Obtiene la lista de todos los usuarios en formato UserDto.
     * @return Lista de UserDto.
     */
    public List<UserDto> findAllDto() {
        logger.info("UserService Listando todos los usuarios como UserDto");
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }
    
   
}

