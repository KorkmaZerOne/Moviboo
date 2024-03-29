/**
 * 
 */
package com.omerkorkmaz.moviboo.data;

import com.omerkorkmaz.moviboo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer>
{

	Role findByName(String name);

}
