package com.tai.travel2.controller;

import com.tai.travel2.model.TicketHistory;
import com.tai.travel2.service.TicketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorApiController {

    @Autowired
    private TicketHistoryService ticketHistoryService;

    @PostMapping("/verify-ticket")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> verifyTicket(@RequestParam String uniqueCode) {
        return ticketHistoryService.verifyTicket(uniqueCode)
                .map(status -> ResponseEntity.ok(status))
                .orElseGet(() -> ResponseEntity.badRequest().body("Ticket Invalid"));
    }
}