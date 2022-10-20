package com.banking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "offer_name")
    private String offerName;
    
    @Column(name = "loan_ammount")
    private Integer loanAmmount;
    
    @Column(name = "interest_rate")
    private double interestRate;
    
    @Column(name = "int_free_cash")
    private Integer intFreeCash;
    
    @Column(name = "annual_fee")
    private Integer annualFee;
    
    @Column(name = "preclosure_charges")
    private Integer preclosureCharges;
    
    @Column(name = "customer_id")
    private Integer customerId;
    

    

}
