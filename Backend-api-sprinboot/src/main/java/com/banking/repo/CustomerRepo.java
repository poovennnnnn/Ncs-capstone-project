package com.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    
    boolean existsById(String id);
}
