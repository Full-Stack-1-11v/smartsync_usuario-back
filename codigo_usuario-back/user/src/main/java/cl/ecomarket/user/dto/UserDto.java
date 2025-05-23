package cl.ecomarket.user.dto;

public class UserDto {
     private Integer id;
    private String name;
    private String email;
    private boolean estado;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

}
