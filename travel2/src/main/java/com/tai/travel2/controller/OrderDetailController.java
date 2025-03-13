package com.tai.travel2.controller;

import com.tai.travel2.model.Buyer;
import com.tai.travel2.model.OrderDetail;
import com.tai.travel2.model.Ticket;
import com.tai.travel2.model.TicketHistory;
import com.tai.travel2.service.BuyerService;
import com.tai.travel2.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private BuyerService buyerService;

    private Buyer getAuthenticatedBuyer() {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return buyerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Buyer not found"));
    }

    @GetMapping
    public String viewCart(Model model) {
        Buyer buyer = getAuthenticatedBuyer();
        OrderDetail orderDetail = orderDetailService.getOrderDetailByBuyerId(buyer.getId());
        if (orderDetail != null) {
            orderDetail.calculateTotalPrice();
        }
        model.addAttribute("orderDetail", orderDetail);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        Buyer buyer = getAuthenticatedBuyer();
        try {
            boolean added = false;
            for (String key : params.keySet()) {
                if (key.startsWith("attractionId-")) {
                    String attractionIdStr = params.get(key);
                    String quantityKey = "quantity-" + attractionIdStr;
                    Long attractionId = Long.parseLong(attractionIdStr);
                    int quantity = Integer.parseInt(params.getOrDefault(quantityKey, "0"));
                    if (quantity > 0) {
                        orderDetailService.addTicketToOrderDetail(buyer.getId(), attractionId, quantity); // Should now work
                        added = true;
                    }
                }
            }
            if (added) {
                redirectAttributes.addFlashAttribute("message", "Tickets added to cart successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "No tickets were added (all quantities were 0).");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add tickets: " + e.getMessage());
        }
        return "redirect:/orderdetail";
    }

    @PostMapping("/remove/{attractionId}")
    public String removeFromCart(@PathVariable Long attractionId, RedirectAttributes redirectAttributes) {
        Buyer buyer = getAuthenticatedBuyer();
        try {
            orderDetailService.removeTicketFromOrderDetail(buyer.getId(), attractionId);
            redirectAttributes.addFlashAttribute("message", "Ticket removed successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to remove ticket: " + e.getMessage());
        }
        return "redirect:/orderdetail";
    }

    @PostMapping("/update")
    public String updateCartQuantity(@RequestParam Long attractionId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        Buyer buyer = getAuthenticatedBuyer();
        try {
            if (quantity <= 0) {
                orderDetailService.removeTicketFromOrderDetail(buyer.getId(), attractionId);
                redirectAttributes.addFlashAttribute("message", "Ticket removed (quantity set to 0)!");
            } else {
                orderDetailService.updateTicketQuantity(buyer.getId(), attractionId, quantity);
                redirectAttributes.addFlashAttribute("message", "Quantity updated successfully!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update quantity: " + e.getMessage());
        }
        return "redirect:/orderdetail";
    }

    @PostMapping("/checkout")
    public String checkoutCart(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Buyer buyer = buyerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Buyer not found"));
        OrderDetail orderDetail = orderDetailService.getOrderDetailByBuyerId(buyer.getId());

        if (orderDetail != null && !orderDetail.getTickets().isEmpty()) {
            orderDetail.calculateTotalPrice();
            model.addAttribute("orderDetail", orderDetail);
            model.addAttribute("buyer", buyer);
            model.addAttribute("totalPrice", orderDetail.getTotalPrice());
            return "checkout-receipt";
        } else {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty. Add tickets before checking out.");
            return "redirect:/orderdetail";
        }
    }

    @PostMapping("/pay")
    public String payAndCompleteOrder(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Buyer buyer = buyerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Buyer not found"));
        OrderDetail orderDetail = orderDetailService.getOrderDetailByBuyerId(buyer.getId());

        if (orderDetail != null && !orderDetail.getTickets().isEmpty()) {
            List<TicketHistory> completedTickets = orderDetailService.completeCheckoutWithHistory(buyer.getId()); // New method
            double totalPrice = completedTickets.stream().mapToDouble(TicketHistory::getTotalPrice).sum();

            model.addAttribute("completedTickets", completedTickets);
            model.addAttribute("totalPrice", totalPrice);
            return "receipt";
        } else {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty or already processed.");
            return "redirect:/orderdetail";
        }
    }
}