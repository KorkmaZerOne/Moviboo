package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
