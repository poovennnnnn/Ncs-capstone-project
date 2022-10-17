package com.banking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.BankAccount;
import com.banking.entity.User;
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;
import com.banking.repo.BankAccountRepo;
import com.banking.repo.UserRepo;

@Service
public class BankAccountServicImpl  implements BankAccountService{
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private BankAccountRepo bankRepo;
 

    @Override
    public BankAccount create(User user) throws AccountAlreadyExistsException {
        
        return userRepo.save(user).getBankAccount();
    }

    @Override
    public BankAccount update(BankAccount account) throws AccountNotFoundException {
         
        return bankRepo.save(account);
    }

    @Override
    public List<BankAccount> findAll() {
        return bankRepo.findAll();
    }

    @Override
    public BankAccount findById(Integer id) throws AccountNotFoundException {
        return bankRepo.findById(id).orElseThrow(()-> new AccountNotFoundException("Account not Found ac.no : "+id));
    }

    @Override
    public BankAccount findByActNumber(Integer accountNumber) throws AccountNotFoundException {
        return bankRepo.findByActNumber(accountNumber).orElseThrow(()-> new AccountNotFoundException("Account not Found ac.no : "+accountNumber));
    }

    @Override
    public boolean existsByActNumber(Integer accountNumber) {
        return bankRepo.existsByActNumber(accountNumber);
    }

    @Override
    public void deleteById(Integer id) {

        bankRepo.deleteById(id);
    }

}
