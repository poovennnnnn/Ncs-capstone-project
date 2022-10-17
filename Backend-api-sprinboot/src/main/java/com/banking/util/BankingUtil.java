package com.banking.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.banking.service.BankAccountService;


@Component
public class BankingUtil {
    
    @Autowired
    private  BankAccountService service;
    
    
    public  Integer getRandomNumber() {
        
     
          long number = (long) Math.floor(Math.random() * 9_000_000L) + 1_000_000_000L;
      
        while(service.existsByActNumber((int)number)) {
            number = (long) Math.floor(Math.random() * 9_000_000L) + 1_000_000_000L;
        }
        return (int) number ;
    }

}
