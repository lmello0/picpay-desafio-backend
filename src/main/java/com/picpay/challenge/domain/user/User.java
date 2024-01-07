package com.picpay.challenge.domain.user;

import com.picpay.challenge.domain.user.DTO.PostUserDTO;
import com.picpay.challenge.domain.user.DTO.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String password;
    private boolean active;

    public User(PostUserDTO data) {
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.cpf = data.cpf();
        this.email = data.email();
        this.password = data.password();
        this.active = true;
    }

    public void updateInfo(UpdateUserDTO data) {
        if (data.firstName() != null) {
            this.firstName = data.firstName();
        }

        if (data.lastName() != null) {
            this.lastName = data.lastName();
        }

        if (data.email() != null) {
            this.email = data.email();
        }

        if (data.cpf() != null) {
            this.cpf = data.cpf();
        }
    }

    public void delete() {
        this.active = false;
    }
}
