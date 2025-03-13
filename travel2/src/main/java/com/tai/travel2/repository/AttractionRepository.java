package com.tai.travel2.repository;

import com.tai.travel2.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    /*List<Attraction> findByNameContainingIgnoreCase(String name);*/
}