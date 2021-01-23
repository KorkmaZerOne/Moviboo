package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Order;
import com.omerkorkmaz.moviboo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);
}
