package cl.ecomarket.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.user.model.User;
import cl.ecomarket.user.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Este método busca por el nombre del usuario y devuelve una lista de usuarios
    public List<User> findAll(){
        return userRepository.findAll();
    }

    // buscar por id
    public User findById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // agregar usuario
    public User save(User user){
        return userRepository.save(user);
    }

    // eliminar usuario
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
    
    // eliminar usuario por estado
    // Este método elimina un usuario por su ID, pero solo si su estado es false
    public void deleteByIdFalse(Integer id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    if (!user.isEstado()) {
        userRepository.deleteById(id);
    } else {
        throw new RuntimeException("No se puede eliminar el usuario porque está habilitado");
        }

    }

    // login 
    public boolean login (String email, String password ) {
        User usuario = userRepository.findByEmail(email);
        return usuario != null && usuario.getPassword().equals(password);
    }

    @Transactional
    public List<User> findByEstadoFalse() {
        return userRepository.findByEstadoFalse();
    }
}

