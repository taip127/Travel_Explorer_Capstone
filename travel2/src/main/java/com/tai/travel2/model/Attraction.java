package com.tai.travel2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private String location;
    private Integer availableTickets;
    @Column(name = "img_url")
    private String imgUrl; // URL to image in static/image folder
    @Column(name = "link_url")
    private String linkUrl; // URL to attraction website
}