package com.tai.travel2.service;

import com.tai.travel2.model.Attraction;
import com.tai.travel2.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttractionService {
    @Autowired
    private AttractionRepository attractionRepository;

    // Get all attractions
    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    // Get attraction by ID
    public Optional<Attraction> getAttractionById(Long id) {
        return attractionRepository.findById(id);
    }

    public Attraction createAttraction(Attraction attraction){
        return attractionRepository.save(attraction);
    }

    public Attraction updateAttraction(Long id, Attraction updatedAttraction){
        return attractionRepository.findById(id)
                .map(attraction -> {
                    attraction.setName(updatedAttraction.getName());
                    attraction.setDescription(updatedAttraction.getDescription());
                    attraction.setPrice(updatedAttraction.getPrice());
                    attraction.setAvailableTickets(updatedAttraction.getAvailableTickets());
                    attraction.setLocation(updatedAttraction.getLocation());
                    return attractionRepository.save(attraction);
                })
                .orElseThrow(()-> new RuntimeException("Attraction not found"));
    }

    // Delete attraction by ID
    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }
}