package com.omerkorkmaz.moviboo;

import com.omerkorkmaz.moviboo.model.Category;
import com.omerkorkmaz.moviboo.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovibooApp {

    public static void main(String[] args) {
        SpringApplication.run(MovibooApp.class, args);


        Category cat1 = new Category();
        Category cat2 = new Category();


        //List<SubCategory> subCategoryList = new ArrayList<>();
        //subCategoryList.add(subCat1);
        //subCategoryList.add(subCat2);

        Product pro1 = new Product();
        Product pro2 = new Product();

        pro1.setSku("bk12345");
        pro1.setTitle("red book");
        pro1.setActor("Omer");
        pro1.setRelease_year("2021");
        pro1.setPrice(10);
        pro1.setCategory(cat1);


        pro2.setSku("mv123456");
        pro2.setTitle("black movie");
        pro2.setActor("Korkmaz");
        pro2.setRelease_year("2021");
        pro2.setPrice(12);
        pro2.setCategory(cat2);






    }

}
