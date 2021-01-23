package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Credit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {
}
