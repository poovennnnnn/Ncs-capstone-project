package com.banking.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Offers;

public interface OffersRepo extends JpaRepository<Offers, Integer> {
    
//   boolean existsById(String id);
//   boolean existsByOfferName(String name);
   boolean existsByCustomerIdAndOfferName(int id,String customer);
}
