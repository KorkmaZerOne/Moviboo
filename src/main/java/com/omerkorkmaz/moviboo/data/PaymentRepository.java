package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
