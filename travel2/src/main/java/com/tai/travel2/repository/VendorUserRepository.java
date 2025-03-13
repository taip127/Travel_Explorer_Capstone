package com.tai.travel2.repository;

import com.tai.travel2.model.VendorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {
    Optional<VendorUser> findByUsername(String username);
}