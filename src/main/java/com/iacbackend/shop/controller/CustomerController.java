package com.iacbackend.shop.controller;

import com.iacbackend.shop.Exceptions.CustomerNotFoundException;
import com.iacbackend.shop.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.iacbackend.shop.model.Customer;

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

    @GetMapping(path="/{id}")
    public @ResponseBody Customer getCustomer (@PathVariable int id) { return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)); }

    @PutMapping(path="/{id}")
    public @ResponseBody Customer updateCustomer (@PathVariable int id, @RequestBody Customer newCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhone(newCustomer.getPhone());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteCustomer (@PathVariable int id) {
        customerRepository.deleteById(id);
        return "Customer Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllUsers() {
        return customerRepository.findAll();
    }


}
