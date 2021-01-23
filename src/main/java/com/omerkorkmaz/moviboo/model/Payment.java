package com.omerkorkmaz.moviboo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "Id")
    private int paymentId;

    @Column(name="Credit_Number")
    private String creditNumber;

    @Column(name = "Amount")
    private double amount;

    @Column(name = "Payment_Date")
    private Date paymentDate;

    @ManyToOne
    private Credit credit;

}
