package com.tai.travel2.service;

import com.tai.travel2.model.Attraction;
import com.tai.travel2.model.Buyer;
import com.tai.travel2.model.OrderDetail;
import com.tai.travel2.repository.AttractionRepository;
import com.tai.travel2.repository.BuyerRepository;
import com.tai.travel2.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Buyer registerBuyer(Buyer buyer) {
        Buyer savedBuyer = buyerRepository.save(buyer);

        //Create and save new Order for the registered Buyer
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setBuyer(savedBuyer);
        orderDetailRepository.save(orderDetail);
        return savedBuyer;
    }

    //public List<Buyer> getAttractionsByCategory(String category) {}

    public Optional<Buyer> findByUsername(String username) {
        return buyerRepository.findByUsername(username);
    }

    public List<Attraction> findAllAtrractions() {return attractionRepository.findAll();}
}
