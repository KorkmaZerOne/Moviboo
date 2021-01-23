package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByMail(String mail);

}