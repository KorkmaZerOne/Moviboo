package com.omerkorkmaz.moviboo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name= "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int productId;

    @Column(name = "Sku", nullable = false , unique = true)
    @NotEmpty(message = "*Please provide sku")
    private String sku;

    @Column(name = "Title" , nullable = false)
    @NotEmpty(message = "*Please provide title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Actor" , nullable = false)
    @NotEmpty(message = "*Please provide actor or author")
    private String actor;

    @Column(name = "Release_Year" , nullable = false)
    @NotEmpty(message = "*Please provide release year")
    private String release_year;

    @Column(name = "Image")
    private String imageURL;

    @Column(name = "Price" , nullable = false)
    @NotEmpty(message = "*Please provide price")
    private double price;

    @Column(name = "Disabled")
    private boolean disabled;

    @Column(name = "Campaign")
    private boolean campaign;

    @Column(name = "Create_Date")
    private Date createDate;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    //@OneToMany (mappedBy = "product")
   // private List<CartItem>  cartItems;

}
