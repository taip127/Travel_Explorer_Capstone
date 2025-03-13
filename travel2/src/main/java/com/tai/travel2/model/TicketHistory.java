package com.tai.travel2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "ticket_history")
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_code", nullable = false, unique = true)
    private String uniqueCode;

    @ManyToOne
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Column(nullable = false)
    private boolean redeemed = false;

    @PrePersist
    public void generateUniqueCode() {
        if (this.uniqueCode == null) {
            this.uniqueCode = UUID.randomUUID().toString();
        }
    }

    public void markAsRedeemed() {
        this.redeemed = true;
    }
}