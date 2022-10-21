package com.banking.util;


import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;


@Component
public class BankingUtil {
    
 
    
    
    public  Integer getRandomNumber() {
        
     
          long number = (long) Math.floor(Math.random() * 9_000_000L) + 1_000_000_000L;
      
        return (int) number ;
    }
    
    
    public int ageOfAccount(Date date) {
        
        
        LocalDate dob = date.toLocalDate(); 
        //obtains the current date from the system clock  
        LocalDate curDate = LocalDate.now();  
        //calculates the difference betwween two dates  
        Period period = Period.between(dob, curDate);
       return period.getYears();
    }

}
