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
 
    @PostMapping("/enviarMonitoreo")
    public String recibirMonitoreo(@RequestBody MonitoreoUserRequest monitoreoUserRequest) {
        MonitoreoDTO monitoreoDTO = monitoreoUserRequest.getMonitoreoDTO();
        User user = monitoreoUserRequest.getUser();

        // Aquí puedes procesar el monitoreoDTO y el user según tus necesidades
        // Por ejemplo, puedes imprimir los valores en la consola
        System.out.println("MonitoreoDTO: " + monitoreoDTO);
        System.out.println("User: " + user);

        return "Datos recibidos correctamente";
    }
}
