package com.tai.travel2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendorController {

    @GetMapping("/vendor")
    public String showVendorPage() {
        return "vendor";
    }
}