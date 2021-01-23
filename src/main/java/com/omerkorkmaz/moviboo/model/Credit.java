package com.omerkorkmaz.moviboo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int creditId;

    @Column(name = "Credit_Number")
    private String creditNumber;

    @Column(name = "Balance")
    private double balance;

    @OneToMany(mappedBy = "credit")
    private List<Payment> payment;

    @OneToOne
    private Customer customer;

}
