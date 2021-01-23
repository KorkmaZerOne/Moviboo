package com.omerkorkmaz.moviboo.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table (name= "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int categoryId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description" , length=1024)
    private String description;

    private boolean disabled;

    @Column(name="Display_Order")
    private Integer displayOrder;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

}