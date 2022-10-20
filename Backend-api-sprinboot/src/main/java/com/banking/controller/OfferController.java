package com.banking.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.BankAccount;
import com.banking.entity.Offers;
import com.banking.exception.AccountNotFoundException;
import com.banking.service.BankAccountService;
import com.banking.service.OfferService;
import com.banking.util.BankingUtil;

@RestController
@RequestMapping("/offer")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BankAccountService bankService;
    
    @Autowired
    private OfferService offerService;


    @Autowired
    private BankingUtil util;


    

    @PostMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> availOffer(@PathVariable int id,@RequestBody Offers offer) throws AccountNotFoundException{

        BankAccount account = bankService.findById(id);
        boolean flag =  offerService.existsByCustomerIdAndOfferName(account.getCustomer().getId(),offer.getOfferName());
        
        if(flag) {
            return new   ResponseEntity<>("Offer Already Availed",HttpStatus.OK);
        }else {
            offer.setCustomerId(account.getCustomer().getId());
            account.getCustomer().getOffers().add(offer);
            bankService.update(account);
            return new   ResponseEntity<>("Offer Availed Successfully",HttpStatus.OK);
        }      
    }
    
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Object> getOffer(@PathVariable int id) throws AccountNotFoundException{
        BankAccount account = bankService.findById(id);
        List<Offers> offerList = new ArrayList<Offers>();
        List<String> strArr = new ArrayList<String>();

        String[] arr = {"CREDITCARD","HOMELOAN","CARLOAN"};
        for(String x :arr) {
           boolean flag =  offerService.existsByCustomerIdAndOfferName(account.getCustomer().getId(), x);
           if(!flag) {
               strArr.add(x);
           }
        }
        for(String x:strArr) {
            offerList.add(generateOffer(x, account));
        }

        return new ResponseEntity<>(offerList,HttpStatus.OK);
    }

    private Offers generateOffer(String str,BankAccount account) {

        Offers offer = new Offers();
        offer.setOfferName(str);

        int ageOfAccount = util.ageOfAccount(account.getActCreationDate());

        if(offer.getOfferName()== "CREDITCARD") {
            if(account.getActBalance() >= 10000) {
                offer.setAnnualFee(50);
            }else if(account.getActBalance() >= 7000 ) {
                offer.setAnnualFee(60);
            }else {
                offer.setAnnualFee(70);
            }

            if(ageOfAccount >= 10) {
                offer.setInterestRate(6);
            }else if(ageOfAccount >= 5) {
                offer.setInterestRate(7);
            }else {
                offer.setInterestRate(9);
            }

            if(account.getActBalance() >= 10000 && ageOfAccount >=10) {
                offer.setIntFreeCash(1000);
            }else if(account.getActBalance() >= 5000 && ageOfAccount >= 5) {
                offer.setIntFreeCash(500);
            }else {
                offer.setIntFreeCash(100);
            }

        }


        if(offer.getOfferName()== "HOMELOAN") {
            if(account.getActType()== "SAVINGS") {
                offer.setLoanAmmount(20000);
            }else if(account.getActType()== "CURRENT") {
                offer.setLoanAmmount(25000);
            }else {
                offer.setLoanAmmount(1000);
            }

            if(offer.getLoanAmmount() >= 25000) {
                offer.setInterestRate(6);
            }else if(offer.getLoanAmmount() >= 20000){
                offer.setInterestRate(7);
            }else {
                offer.setInterestRate(9);
            }


            if(offer.getInterestRate() == 7) {
                offer.setPreclosureCharges(100);
            }else  if(offer.getInterestRate() == 6){
                offer.setPreclosureCharges(80);

            }else {
                offer.setPreclosureCharges(20);
            }


        }

        if(offer.getOfferName()== "CARLOAN") {

            int fixedAmmount = 1000;
            int fixedInterest = 6;
            if(account.getActType()=="SAVINGS") {
                offer.setLoanAmmount(fixedAmmount+2000);
            }else if(account.getActType()=="CURRENT" ) {
                offer.setLoanAmmount(fixedAmmount+2500);
            }else {
                offer.setLoanAmmount(fixedAmmount);
            }

            if(account.getActBalance() >= 15000) {
                offer.setInterestRate(fixedInterest +1.5);
            }else if(account.getActBalance() >= 15000 & account.getActBalance() < 15000) {
                offer.setInterestRate(fixedInterest +1.2);
            }else {
                offer.setInterestRate(fixedInterest);
            }

            offer.setPreclosureCharges(0);
        }

        return offer;
    }


}
