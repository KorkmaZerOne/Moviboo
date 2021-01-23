package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category getByName(String name);
}
