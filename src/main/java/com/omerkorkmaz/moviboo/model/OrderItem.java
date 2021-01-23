package com.omerkorkmaz.moviboo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orderItems")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int orderItemId;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

}
