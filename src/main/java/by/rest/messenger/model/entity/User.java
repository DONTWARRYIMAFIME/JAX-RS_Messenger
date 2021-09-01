package by.rest.messenger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_login_unique", columnNames = "login")
        }
)
public class User {

    @Id
    @Column(
            name = "login",
            nullable = false,
            unique = true,
            updatable = false,
            length = 32
    )
    private String login;

    @Column(
            name = "first_name",
            nullable = false,
            length = 64
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 128
    )
    private String lastName;

    @Column(
            name = "dob",
            nullable = false
    )
    private LocalDate dob;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

}