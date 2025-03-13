package com.tai.travel2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double price;

    public double getTotalPrice() {return quantity*price;}

    @ManyToOne
    @JoinColumn(name = "orderdetail_id")
    private OrderDetail orderdetail;

    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
}