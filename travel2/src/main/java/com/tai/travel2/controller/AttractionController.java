package com.tai.travel2.controller;

import com.tai.travel2.model.Attraction;
import com.tai.travel2.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/attractions")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    // Admin-only: Show all attractions for management
    @GetMapping("/manage")
    public String getAllAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "manage-attractions"; // Matches manage-attractions.html
    }

    // Admin-only: Show form to add new attraction
    @GetMapping("/new")
    public String showCreateAttractionForm(Model model) {
        Attraction attraction = new Attraction();
        model.addAttribute("attraction", attraction);
        return "attraction-form"; // Matches attraction-form.html
    }

    // Admin-only: Show form to edit existing attraction
    @GetMapping("/edit/{id}")
    public String showEditAttractionForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Attraction> optionalAttraction = attractionService.getAttractionById(id);
        if (optionalAttraction.isPresent()) {
            model.addAttribute("attraction", optionalAttraction.get());
            return "attraction-form"; // Matches attraction-form.html
        } else {
            redirectAttributes.addFlashAttribute("error", "Attraction not found.");
            return "redirect:/attractions/manage";
        }
    }

    // Admin-only: Save new or updated attraction
    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute("attraction") Attraction attraction, RedirectAttributes redirectAttributes) {
        try {
            if (attraction.getId() != null) {
                attractionService.updateAttraction(attraction.getId(), attraction);
                redirectAttributes.addFlashAttribute("message", "Attraction updated successfully!");
            } else {
                attractionService.createAttraction(attraction);
                redirectAttributes.addFlashAttribute("message", "Attraction added successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save attraction: " + e.getMessage());
        }
        return "redirect:/attractions/manage";
    }

    // Admin-only: Delete attraction
    @PostMapping("/delete/{id}")
    public String deleteAttraction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            attractionService.deleteAttraction(id);
            redirectAttributes.addFlashAttribute("message", "Attraction deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete attraction: " + e.getMessage());
        }
        return "redirect:/attractions/manage";
    }

    // Buyer-facing: Browse attractions (assuming this exists separately)
    @GetMapping
    public String browseAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions"; // Matches your buyer-facing attractions.html
    }
}