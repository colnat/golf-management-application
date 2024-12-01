package coltonbertrand.golf_management_application.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name="first_name")
    @NotBlank
    private String first_name;

    @Column(name="last_name")
    @NotBlank
    private String last_name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name="password")
    @NotBlank
    @Size(min=5)
    private String password;
}
