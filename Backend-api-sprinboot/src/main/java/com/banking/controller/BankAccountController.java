package com.banking.controller;


import java.security.Principal;
import java.util.List;

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
import com.banking.exception.AccountAlreadyExistsException;
import com.banking.exception.AccountNotFoundException;
import com.banking.service.BankAccountService;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200")
public class BankAccountController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BankAccountService bankService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankAccount> create(@RequestBody UserRequest userRequest) throws AccountAlreadyExistsException {

      return new ResponseEntity<BankAccount>(bankService.create(userRequest),HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankAccount> update(@RequestBody UserRequest userRequest) throws AccountNotFoundException {

        if(!bankService.existsByActNumber(userRequest.getActNumber())) {

            throw new AccountNotFoundException("Account Not Found ac.no: "+userRequest.getActNumber());
        } 
        return new ResponseEntity<BankAccount>(bankService.update(userRequest),HttpStatus.OK);
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


        BankAccount account = bankService.findById(id);

        return ResponseEntity.ok(account);        
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable int id) throws AccountNotFoundException{


        bankService.findById(id);
        bankService.deleteById(id);

        return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);

    }


}
