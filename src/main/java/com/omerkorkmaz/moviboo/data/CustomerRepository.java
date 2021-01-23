/**
 * 
 */
package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Customer;
import com.omerkorkmaz.moviboo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByEmail(String email);

	@Query("select o from Order o where o.customer.email=?1")
	List<Order> getCustomerOrders(String email);

}
