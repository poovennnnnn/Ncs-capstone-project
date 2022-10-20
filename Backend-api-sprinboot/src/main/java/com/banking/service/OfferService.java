package com.banking.service;

public interface OfferService {
    boolean existsByCustomerIdAndOfferName(int id,String customer);
}
