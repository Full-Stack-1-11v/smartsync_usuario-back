package cl.ecomarket.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.dto.MonitoreoDTO;
import cl.ecomarket.user.dto.MonitoreoUserRequest;
import cl.ecomarket.user.model.User;



@RestController
@RequestMapping("/reporte")
public class ReporteDtoController {
 
     @PostMapping("/recibir")
public String recibirMonitoreo(@RequestBody MonitoreoUserRequest request) {
    MonitoreoDTO monitoreoDTO = request.getMonitoreoDTO();
    User user = request.getUser();
    return "Recibido monitoreo: " + monitoreoDTO.getDescripcion() +
           " estado = " + monitoreoDTO.getEstado() +
           " id monitoreo = " + monitoreoDTO.getId() +
           " id usuario = " + user.getId() +
           " nombre user = " + user.getName();
        }
}
