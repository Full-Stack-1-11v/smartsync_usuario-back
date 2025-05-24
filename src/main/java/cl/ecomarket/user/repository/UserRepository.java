package cl.ecomarket.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cl.ecomarket.user.model.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // este medotodo busca por el nombre del usuario y devuelve una lista de usuarios
    List<User> findByName(String name);    
    User findByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.estado = false")
    List<User> findByEstadoFalse();
}
