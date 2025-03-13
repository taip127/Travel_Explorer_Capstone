package com.tai.travel2.service;

import com.tai.travel2.model.*;
import com.tai.travel2.repository.AttractionRepository;
import com.tai.travel2.repository.BuyerRepository;
import com.tai.travel2.repository.OrderDetailRepository;
import com.tai.travel2.repository.TicketHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    @Autowired
    private OrderDetailRepository orderdetailRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;

    // Ensure this method exists and matches the signature
    @Transactional(readOnly = true)
    public OrderDetail getOrderDetailByBuyerId(Long buyerId) {
        logger.info("Fetching OrderDetail for buyerId: {}", buyerId);
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        OrderDetail orderDetail = orderdetailRepository.findByBuyer(buyer).orElseGet(() -> {
            logger.info("No existing OrderDetail found, creating new for buyerId: {}", buyerId);
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setBuyer(buyer);
            return orderdetailRepository.save(newOrderDetail);
        });
        orderDetail.getTickets().size(); // Force lazy loading of tickets
        return orderDetail;
    }

    public Long getBuyerIdByUsername(String username) {
        Buyer buyer = buyerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Buyer not found with username: " + username));
        return buyer.getId();
    }

    public OrderDetail addTicketToOrderDetail(Long buyerId, Long attractionId, int quantity) {
        OrderDetail orderDetail = getOrderDetailByBuyerId(buyerId);
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));

        if (quantity > attraction.getAvailableTickets()) {
            throw new RuntimeException("Requested quantity exceeds available tickets for " + attraction.getName());
        }

        Optional<Ticket> existingTicket = orderDetail.getTickets().stream()
                .filter(ticket -> ticket.getAttraction().getId().equals(attractionId))
                .findFirst();

        if (existingTicket.isPresent()) {
            Ticket ticket = existingTicket.get();
            int newQuantity = ticket.getQuantity() + quantity;
            if (newQuantity > attraction.getAvailableTickets()) {
                throw new RuntimeException("Updated quantity exceeds available tickets for " + attraction.getName());
            }
            ticket.setQuantity(newQuantity);
        } else {
            Ticket newTicket = new Ticket();
            newTicket.setAttraction(attraction);
            newTicket.setQuantity(quantity);
            newTicket.setPrice(attraction.getPrice());
            newTicket.setOrderdetail(orderDetail);
            orderDetail.getTickets().add(newTicket);
        }
        updateOrderTotal(orderDetail);
        return orderdetailRepository.save(orderDetail);
    }

    public OrderDetail removeTicketFromOrderDetail(Long buyerId, Long attractionId) {
        OrderDetail orderDetail = getOrderDetailByBuyerId(buyerId);
        Optional<Ticket> ticketToRemove = orderDetail.getTickets().stream()
                .filter(ticket -> ticket.getAttraction().getId().equals(attractionId))
                .findFirst();
        if (ticketToRemove.isPresent()) {
            orderDetail.getTickets().remove(ticketToRemove.get());
            updateOrderTotal(orderDetail);
            return orderdetailRepository.save(orderDetail);
        } else {
            throw new RuntimeException("Ticket not found for attraction ID: " + attractionId);
        }
    }

    public OrderDetail updateTicketQuantity(Long buyerId, Long attractionId, int newQuantity) {
        OrderDetail orderDetail = getOrderDetailByBuyerId(buyerId);
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));

        if (newQuantity > attraction.getAvailableTickets()) {
            throw new RuntimeException("Updated quantity exceeds available tickets for " + attraction.getName());
        }

        Optional<Ticket> ticketToUpdate = orderDetail.getTickets().stream()
                .filter(ticket -> ticket.getAttraction().getId().equals(attractionId))
                .findFirst();
        if (ticketToUpdate.isPresent()) {
            ticketToUpdate.get().setQuantity(newQuantity);
            updateOrderTotal(orderDetail);
            return orderdetailRepository.save(orderDetail);
        } else {
            throw new RuntimeException("Ticket not found for attraction ID: " + attractionId);
        }
    }

    @Transactional
    public List<TicketHistory> completeCheckoutWithHistory(Long buyerId) {
        logger.info("Starting checkout for buyerId: {}", buyerId);
        OrderDetail orderDetail = getOrderDetailByBuyerId(buyerId);
        if (orderDetail.getTickets().isEmpty()) {
            logger.warn("Cart is empty for buyerId: {}", buyerId);
            throw new RuntimeException("Cart is empty, nothing to checkout.");
        }

        List<TicketHistory> histories = new ArrayList<>();
        try {
            for (Ticket ticket : orderDetail.getTickets()) {
                logger.info("Processing ticket for attraction: {}, quantity: {}", ticket.getAttraction().getName(), ticket.getQuantity());
                TicketHistory history = new TicketHistory();
                history.setAttraction(ticket.getAttraction());
                history.setQuantity(ticket.getQuantity());
                history.setPrice(ticket.getPrice());
                history.setTotalPrice(ticket.getTotalPrice());
                history.setBuyer(orderDetail.getBuyer());
                TicketHistory savedHistory = ticketHistoryRepository.save(history);
                histories.add(savedHistory);
                logger.info("Saved TicketHistory with uniqueCode: {}", savedHistory.getUniqueCode());

                Attraction attraction = ticket.getAttraction();
                int newAvailableTickets = attraction.getAvailableTickets() - ticket.getQuantity();
                if (newAvailableTickets < 0) {
                    logger.error("Not enough tickets for {}", attraction.getName());
                    throw new RuntimeException("Not enough tickets available for " + attraction.getName());
                }
                attraction.setAvailableTickets(newAvailableTickets);
                attractionRepository.save(attraction);
                logger.info("Updated availableTickets for {} to {}", attraction.getName(), newAvailableTickets);
            }

            orderDetail.getTickets().clear();
            orderDetail.setTotalPrice(0.0);
            orderdetailRepository.save(orderDetail);
            logger.info("Cart cleared and OrderDetail saved for buyerId: {}", buyerId);
        } catch (Exception e) {
            logger.error("Checkout failed for buyerId: {}", buyerId, e);
            throw e;
        }
        return histories;
    }

    public void updateOrderTotal(OrderDetail orderDetail) {
        orderDetail.calculateTotalPrice();
    }
}