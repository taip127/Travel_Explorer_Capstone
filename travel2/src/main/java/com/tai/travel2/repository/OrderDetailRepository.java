package com.tai.travel2.repository;

import com.tai.travel2.model.Buyer;
import com.tai.travel2.model.OrderDetail;
import com.tai.travel2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByBuyer(Buyer buyer);


    /*// Find all orders for a specific user
    List<OrderDetail> findByUser(User user);

    // Find an order with its details (user and attraction) eagerly fetched
    @Query("SELECT o FROM OrderDetail o JOIN FETCH o.user JOIN FETCH o.attraction WHERE o.id = :orderId")
    OrderDetail findByIdWithDetails(@Param("orderId") Long orderId);*/
}
