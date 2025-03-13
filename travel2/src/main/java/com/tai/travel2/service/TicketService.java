package com.tai.travel2.service;

import com.tai.travel2.model.Ticket;
import com.tai.travel2.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class TicketService {
    /*@Autowired
    private TicketRepository ticketRepository;

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll(); // This will now eagerly fetch user and attraction
    }

    // Get ticket by ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    // Add a new ticket
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Update an existing ticket
    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setTicketCode(ticketDetails.getTicketCode());
        ticket.setOrder(ticketDetails.getOrder());
        ticket.setUser(ticketDetails.getUser());
        ticket.setAttraction(ticketDetails.getAttraction());
        return ticketRepository.save(ticket);
    }

    // Delete a ticket
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }*/
}
