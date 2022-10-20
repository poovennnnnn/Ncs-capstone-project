package com.banking.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.dto.UserRequest;
import com.banking.entity.BankAccount;
import com.banking.entity.Customer;
import com.banking.entity.Role;
import com.banking.entity.User;
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;
import com.banking.repo.BankAccountRepo;
import com.banking.repo.UserRepo;
import com.banking.util.BankingUtil;

@Service
public class BankAccountServicImpl  implements BankAccountService{
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private BankAccountRepo bankRepo;
    

    @Autowired
    private BankingUtil util;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

 


  @Override
     public BankAccount create(UserRequest userRequest) throws AccountAlreadyExistsException {
      
      User user= new User();
      BankAccount account = new BankAccount();
      Customer customer = new Customer();
      Set<Role> role = new HashSet<Role>();
      role.add(new Role( "ROLE_USER","User Role"));
      customer.setFirstName(userRequest.getFirstName());
      customer.setLastName(userRequest.getLastName());
      customer.setCity(userRequest.getCity());
      customer.setPhone(userRequest.getPhone());
      int newAccountNumber = util.getRandomNumber() ;
      while(bankRepo.existsByActNumber(newAccountNumber)) {
          newAccountNumber = util.getRandomNumber();
          
      }

      account.setActNumber(newAccountNumber);
      account.setActCreationDate(new java.sql.Date(System.currentTimeMillis()));
      System.out.println(account.getActNumber());
      account.setActBalance(userRequest.getActBalance());
      account.setActType(userRequest.getActType());

      account.setCustomer(customer);

      //        String username = userRequest.getFirstName()+userRequest.getLastName();
      user.setUsername(userRequest.getUsername());
      user.setPassword(passwordEncoder.encode(userRequest.getUsername()+"@123"));
     
      user.setBankAccount(account);
      user.setRole(role);

      
      return userRepo.save(user).getBankAccount();
  }

    @Override
    public BankAccount update(UserRequest userRequest) throws AccountNotFoundException {

        BankAccount account = bankRepo.findByActNumber(userRequest.getActNumber()).get();
        Customer customer = account.getCustomer();

        customer.setFirstName(userRequest.getFirstName());
        customer.setLastName(userRequest.getLastName());
        customer.setCity(userRequest.getCity());
        customer.setPhone(userRequest.getPhone());
        account.setActBalance(userRequest.getActBalance());
        account.setActType(userRequest.getActType());
         
        return bankRepo.save(account);
    }
    
    @Override
    public BankAccount update(BankAccount account) {

       
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
