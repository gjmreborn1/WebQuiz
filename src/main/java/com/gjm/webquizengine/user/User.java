package com.gjm.webquizengine.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "quiz_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    @Column(unique = true)
    private String username;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 5, message = "Password must have at least 5 characters")
    private String password;

    @NotNull(message = "Email can't be null")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email must be an valid email")
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public void encodePassword() {
        password = BCrypt.withDefaults()
                .hashToString(12, password.toCharArray());
    }

    public boolean equalsPassword(String password) {
        return BCrypt.verifyer()
                .verify(password.toCharArray(), this.password)
                .verified;
    }
}
