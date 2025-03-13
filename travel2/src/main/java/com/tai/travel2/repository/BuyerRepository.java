package com.tai.travel2.repository;

import com.tai.travel2.model.Attraction;
import com.tai.travel2.model.Buyer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {
    Optional<Buyer> findByUsername(String username);
    /*List<Attraction> findByCategory(String category);*/
}
