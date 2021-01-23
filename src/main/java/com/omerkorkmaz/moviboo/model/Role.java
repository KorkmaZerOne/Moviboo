package com.omerkorkmaz.moviboo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id")
    private int userRoleId;

    @Column(name = "Name" , nullable=false, unique=true)
    @NotEmpty
    private String name;

    @Column(name = "Description" , length=500)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    private List<Consent> consents;

}
