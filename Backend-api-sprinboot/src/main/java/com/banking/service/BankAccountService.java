package com.banking.service;

import java.util.List;

import com.banking.dto.UserRequest;
import com.banking.entity.BankAccount;
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;

public interface BankAccountService {

    BankAccount create(UserRequest userRequest) throws AccountAlreadyExistsException;
    BankAccount update(UserRequest userRequest) throws AccountNotFoundException;
    BankAccount update(BankAccount account);
    List<BankAccount> findAll();
    BankAccount findById(Integer id) throws AccountNotFoundException;
    BankAccount findByActNumber(Integer accountNumber) throws AccountNotFoundException;
    boolean existsByActNumber(Integer accountNumber);
    void deleteById(Integer id);
   

}
