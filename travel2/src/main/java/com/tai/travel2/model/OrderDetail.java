package com.tai.travel2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double totalPrice;

    @Column
    private Integer quantity;

    @OneToOne
    @JoinColumn(name ="buyer_id")
    private Buyer buyer;

    @OneToMany(mappedBy = "orderdetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public void calculateTotalPrice() {
        totalPrice = tickets.stream()
                .mapToDouble(Ticket::getTotalPrice)
                .sum();
    }

    /*@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Ensure this field exists

    @ManyToOne
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;*/

}