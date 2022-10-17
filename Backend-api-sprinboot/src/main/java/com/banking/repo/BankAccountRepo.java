package com.banking.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.banking.entity.BankAccount;



public interface BankAccountRepo extends JpaRepository<BankAccount, Integer> {
    
    Optional<BankAccount> findByActNumber(Integer accountNumber);
    boolean existsByActNumber(Integer accountNumber);

}
