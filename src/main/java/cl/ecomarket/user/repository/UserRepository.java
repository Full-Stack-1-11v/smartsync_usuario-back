package cl.ecomarket.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cl.ecomarket.user.model.User;
import java.util.List;

/**
 * Repositorio para la entidad User.
 * Proporciona métodos para acceder ,guardar y consultar usuarios en la base de datos.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Busca usuarios por su nombre.
     * @param name Nombre del usuario.
     */
    List<User> findByName(String name); 
    /**
     * Busca un usuario por su email.
     * @param email Email del usuario.
     */   
    User findByEmail(String email);


    /**
     * Obtiene la lista de usuarios inactivos (estado = false).
     */
    @Query("SELECT u FROM User u WHERE u.estado = false")
    List<User> findByEstadoFalse();
}
