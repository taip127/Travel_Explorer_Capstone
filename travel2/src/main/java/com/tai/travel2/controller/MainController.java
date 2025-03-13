package com.tai.travel2.controller;

import com.tai.travel2.model.Buyer;
import com.tai.travel2.service.BuyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    // Show the home page
    @GetMapping("/")
    public String getHome() {
        return "home";
    }


    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("buyer") Buyer buyer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register"; // Return back to the registration form if there are validation errors
        }
        buyer.setPassword(passwordEncoder.encode(buyer.getPassword()));
        buyerService.registerBuyer(buyer);
        return "redirect:/login";
    }
    @GetMapping("/destinations")
    public String getDestinationsPage() {return "destinations";}

    @GetMapping("/contact")
    public String getContactPage() {return "contact";}

    /*@GetMapping("/manage-attractions")
    public String mangeAttractionsPage() {return "manage-attractions";}*/

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuth(Authentication authentication) {
        return ResponseEntity.ok("Authenticated as: " + authentication.getName() + ", Roles: " + authentication.getAuthorities());
    }
}