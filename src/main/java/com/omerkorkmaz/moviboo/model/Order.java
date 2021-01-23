package com.omerkorkmaz.moviboo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int orderId;

    @Column(name = "Order_Number", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "Create_Date", nullable = false)
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Payment payment;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address deliveryAddress;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address billingAddress;

}
