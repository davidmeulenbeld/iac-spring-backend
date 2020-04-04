package com.iacbackend.shop.controller;

import com.iacbackend.shop.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iacbackend.shop.model.Customer;
import com.iacbackend.shop.model.repository.CustomerRepository;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {
    @Autowired

    private CustomerRepository customerRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {

        Customer c = new Customer();
        c.setName(name);
        c.setEmail(email);
        customerRepository.save(c);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllUsers() {
        return customerRepository.findAll();
    }
}
