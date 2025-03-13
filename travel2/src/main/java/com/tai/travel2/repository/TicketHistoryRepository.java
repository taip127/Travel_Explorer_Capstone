package com.tai.travel2.repository;

import com.tai.travel2.model.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {
    Optional<TicketHistory> findByUniqueCode(String uniqueCode);
}