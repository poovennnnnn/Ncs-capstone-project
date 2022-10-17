package com.banking.service;

import java.util.List;


import com.banking.entity.BankAccount;
import com.banking.entity.User;
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;

public interface BankAccountService {

    BankAccount create(User user) throws AccountAlreadyExistsException;
    BankAccount update(BankAccount account) throws AccountNotFoundException;
    List<BankAccount> findAll();
    BankAccount findById(Integer id) throws AccountNotFoundException;
    BankAccount findByActNumber(Integer accountNumber) throws AccountNotFoundException;
    boolean existsByActNumber(Integer accountNumber);
    void deleteById(Integer id);
   

}
