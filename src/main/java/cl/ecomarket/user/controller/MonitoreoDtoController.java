package cl.ecomarket.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.dto.MonitoreoDTO;
import cl.ecomarket.user.dto.MonitoreoUserRequest;
import cl.ecomarket.user.model.User;



@RestController
@RequestMapping("/api/v1/monitoreo")
public class MonitoreoDtoController {
 
    @PostMapping("/enviarMonitoreo")
    public ResponseEntity<String> recibirMonitoreo(@RequestBody MonitoreoUserRequest monitoreoUserRequest) {
        try {
            MonitoreoDTO monitoreoDTO = monitoreoUserRequest.getMonitoreoDTO();
            User user = monitoreoUserRequest.getUser();

            System.out.println("Monitoreo recibido: " + monitoreoDTO);
            System.out.println("Usuario: " + user);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar el monitoreo");
        }
        return ResponseEntity.ok("Monitoreo recibido correctamente");
    }
    
}
