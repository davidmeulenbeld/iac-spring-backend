package com.iacbackend.shop.controller;

import com.iacbackend.shop.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.iacbackend.shop.model.Customer;
import com.iacbackend.shop.model.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @RequestMapping("/welcome")
    public @ResponseBody String welcome () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return "welcome " + ((UserDetails)principal).getUsername();
        } else {
            return "welcome " + principal.toString();
        }

    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email
            , @RequestParam String username
            , @RequestParam String password ) {

        Customer c = new Customer();
        c.setName(name);
        c.setEmail(email);
        c.setUsername(username);
        customerRepository.save(c);

        // authorities to be granted
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        User customer = new User(c.getUsername(), encoder.encode(password), authorities);

        jdbcUserDetailsManager.createUser(User.withUsername(c.getUsername()).password(encoder.encode(password)).roles("USER", "ADMIN").build());

        return "Saved";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllUsers() {
        return customerRepository.findAll();
    }
}
