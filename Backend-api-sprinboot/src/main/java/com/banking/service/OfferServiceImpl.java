package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.repo.OffersRepo;

@Service
public class OfferServiceImpl implements OfferService {
    
    @Autowired
    private OffersRepo offerRepo;

    @Override
    public boolean existsByCustomerIdAndOfferName(int id, String customer) {
        return offerRepo.existsByCustomerIdAndOfferName(id, customer) ;
    }

}
