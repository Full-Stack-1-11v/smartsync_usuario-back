package cl.ecomarket.user.dto;

import cl.ecomarket.user.model.User;
import lombok.Data;
@Data
public class MonitoreoUserRequest {
    private MonitoreoDTO monitoreoDTO;
    private User user;
}
