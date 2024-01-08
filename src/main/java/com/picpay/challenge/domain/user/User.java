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
    private String document;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int wallet;
    private boolean active;

    public User(PostUserDTO data) {
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.email = data.email();
        this.password = data.password();
        this.type = data.type();
        this.active = true;
        this.wallet = 0;
    }

    public void deposit(int value) {
        this.wallet += value;
    }

    public void withdraw(int value) {
        this.wallet -= value;
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

        if (data.document() != null) {
            this.document = data.document();
        }
    }

    public void delete() {
        this.active = false;
    }
}
