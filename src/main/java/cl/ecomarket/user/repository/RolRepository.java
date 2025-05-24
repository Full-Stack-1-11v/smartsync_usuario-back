package cl.ecomarket.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.ecomarket.user.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
   
    }

