package cl.ecomarket.user.dto;

import cl.ecomarket.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoreoUserRequest {
    private MonitoreoDTO monitoreoDTO;
    private User user;
}
