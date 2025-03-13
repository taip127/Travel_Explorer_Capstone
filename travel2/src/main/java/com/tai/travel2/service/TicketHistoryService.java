package com.tai.travel2.service;

import com.tai.travel2.model.TicketHistory;
import com.tai.travel2.repository.TicketHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TicketHistoryService {

    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;

    @Transactional
    public Optional<String> verifyTicket(String uniqueCode) {
        Optional<TicketHistory> ticketOpt = ticketHistoryRepository.findByUniqueCode(uniqueCode);
        if (!ticketOpt.isPresent()) {
            return Optional.empty(); // Ticket Invalid
        }

        TicketHistory ticket = ticketOpt.get();
        if (ticket.isRedeemed()) {
            return Optional.of("This ticket was already redeemed");
        } else {
            ticket.markAsRedeemed();
            ticketHistoryRepository.save(ticket);
            return Optional.of("Ticket Verified");
        }
    }
}