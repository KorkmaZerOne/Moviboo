package com.omerkorkmaz.moviboo.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int userId;

    @Column(name = "First_Name", length = 20, nullable = false)
    @NotEmpty(message = "*Please provide your first name")
    private String firstName;

    @Column(name = "Last_Name", length = 20, nullable = false)
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "Email" , unique = true, nullable = false)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "Password", nullable = false)
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "Matching_Password", nullable = false)
    @NotEmpty(message = "*Please confirm your password")
    private String matchingPassword;

    @Column(name = "Password_Token", nullable = false)
    private String passwordResetToken;

    @ManyToMany(cascade=CascadeType.MERGE)
    private List<Role> roles;

}