package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Category;
import com.omerkorkmaz.moviboo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {

    Product findByName(String name);


    Product findBySku(String sku);
    @Query("select p from Product p where p.title like ?1 or p.sku like ?1 or p.description like ?1")
    List<Product> search(String query);

}
