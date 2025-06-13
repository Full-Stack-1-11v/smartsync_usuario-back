package cl.ecomarket.user.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.model.Rol;

import cl.ecomarket.user.service.RolService;
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

    Rol rol = new Rol();

    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolService.listaList();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roles);
        }
    }
    


  @GetMapping("/{id}/buscar")
    public ResponseEntity<Rol> buscarRolPorId(@PathVariable Integer id) {
    try {
        Rol rol = rolService.findById(id);
        return ResponseEntity.ok(rol);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
    
  }

    

    @PostMapping("/guardar")
    public ResponseEntity<Rol> guardar(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
        } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    
        }
        
    }
 
 


}
