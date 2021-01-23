package com.omerkorkmaz.moviboo.security;


import com.omerkorkmaz.moviboo.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;


public class AuthenticatedCustomer extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;
    private Customer customer;

    public AuthenticatedCustomer(Customer customer) {
        super(customer.getEmail(), customer.getPassword(), getAuthorities(customer));
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Customer customer) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        return authorities;
    }
}

