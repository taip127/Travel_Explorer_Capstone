package com.tai.travel2.service;

import com.tai.travel2.model.AdminUser;
import com.tai.travel2.model.Buyer;
import com.tai.travel2.model.VendorUser;
import com.tai.travel2.repository.AdminUserRepository;
import com.tai.travel2.repository.BuyerRepository;
import com.tai.travel2.repository.VendorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private VendorUserRepository vendorUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check Admin
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        if (adminUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    adminUser.get().getUsername(),
                    adminUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + adminUser.get().getRole().name()))
            );
        }

        // Check Buyer
        Optional<Buyer> buyer = buyerRepository.findByUsername(username);
        if (buyer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    buyer.get().getUsername(),
                    buyer.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + buyer.get().getRole().name()))
            );
        }

        // Check Vendor
        Optional<VendorUser> vendorUser = vendorUserRepository.findByUsername(username);
        if (vendorUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    vendorUser.get().getUsername(),
                    vendorUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + vendorUser.get().getRole().name()))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}