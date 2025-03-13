package com.tai.travel2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "buyer")
@PrimaryKeyJoinColumn(name = "id")
public class Buyer extends User {
    public Buyer() {}
}
