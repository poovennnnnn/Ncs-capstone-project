package com.banking.controller;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.UserRequest;
import com.banking.entity.BankAccount;
import com.banking.entity.Customer;
import com.banking.entity.Role;
import com.banking.entity.User;
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;
import com.banking.service.BankAccountService;
import com.banking.util.BankingUtil;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200")
public class BankAccountController {
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BankAccountService bankService;


    @Autowired
    private BankingUtil util;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankAccount> create(@RequestBody UserRequest userRequest) throws AccountAlreadyExistsException {

        if(bankService.existsByActNumber(userRequest.getActNumber())) {

            throw new AccountAlreadyExistsException("Account Not Found ac.no: "+userRequest.getActNumber());
        } 


        User user= new User();
        BankAccount account = new BankAccount();
        Customer customer = new Customer();
        Set<Role> role = new HashSet<Role>();
        role.add(new Role( "ROLE_USER","User Role"));
        customer.setFirstName(userRequest.getFirstName());
        customer.setLastName(userRequest.getLastName());
        customer.setCity(userRequest.getCity());
        customer.setPhone(userRequest.getPhone());

        account.setActNumber(util.getRandomNumber());
        account.setActCreationDate(new java.sql.Date(System.currentTimeMillis()));
        System.out.println(account.getActNumber());
        account.setActBalance(userRequest.getBalance());
        account.setActType(userRequest.getActType());
        
        account.setCustomer(customer);
 
        String username = userRequest.getFirstName()+userRequest.getLastName();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("password"));
        user.setBankAccount(account);
        user.setRole(role);
        
        
        return new ResponseEntity<BankAccount>(bankService.create(user),HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankAccount> update(@RequestBody UserRequest userRequest) throws AccountNotFoundException {
        
        if(!bankService.existsByActNumber(userRequest.getActNumber())) {

            throw new AccountNotFoundException("Account Not Found ac.no: "+userRequest.getActNumber());
        } 


        BankAccount account = bankService.findByActNumber(userRequest.getActNumber());
        Customer customer = account.getCustomer();

        customer.setFirstName(userRequest.getFirstName());
        customer.setLastName(userRequest.getLastName());
        customer.setCity(userRequest.getCity());
        customer.setPhone(userRequest.getPhone());
        account.setActBalance(userRequest.getBalance());
        account.setActType(userRequest.getActType());

        return new ResponseEntity<BankAccount>(bankService.update(account),HttpStatus.OK);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BankAccount>> findAllAccount(){
        
        List<BankAccount> accountList = bankService.findAll();
        
        return ResponseEntity.ok(accountList);        
    }
    
    
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BankAccount> findById(@PathVariable int id, Principal principal) throws AccountNotFoundException{
        
//        User user  = userService.findByUsername(principal.getName())
//                                .orElseThrow(()-> 
//                                new AccountNotFoundException("Account not Found ac.no : "+id));
//        if(user.getBankAccount().getId() != id) {
//            System.out.println("Some Principal id: "+user.getBankAccount().getId());
//            throw new AccountNotFoundException
//            ("Not allowed to make this request principal id :"
//            +user.getBankAccount().getId()+" path id is :"+id);
//            
//            String str = "Not allowed to make this request principal id :"+user.getBankAccount().getId()+" path id is :"+id;
//                  
//            
//            
//            
//        }
//        
        
        
        BankAccount account = bankService.findById(id);
        
        return ResponseEntity.ok(account);        
    }
    
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable int id) throws AccountNotFoundException{
        
        //custom login for finding user and delete
//        BankAccount account =  bankService.findById(id);
//        String username = account.getCustomer().getFirstName()+ account.getCustomer().getLastName();
//        int userId = userService.findByUsername(username).get().getId();
//        
//        
//        
//        userService.deleteById(id);
        bankService.findById(id);
        bankService.deleteById(id);
        
        return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
        
    }
    
    

}
