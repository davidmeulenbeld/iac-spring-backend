package com.iacbackend.shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CustomerController {

    @RequestMapping("/")
    public String getCustomer() {
        return "joeeeee";
    }
}
