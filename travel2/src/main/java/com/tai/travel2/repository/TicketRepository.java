package com.tai.travel2.repository;

import com.tai.travel2.model.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /*@EntityGraph(attributePaths = {"user", "attraction"})
    List<Ticket> findAll();*/
}