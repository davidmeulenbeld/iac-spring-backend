package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.CustomerModelAssembler;
import com.iacbackend.shop.Exceptions.ProductNotFoundException;
import com.iacbackend.shop.model.Address;
import com.iacbackend.shop.model.repository.AddressRepository;
import com.iacbackend.shop.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.iacbackend.shop.model.Customer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {

    @Autowired
    private CustomerModelAssembler assembler;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestBody Customer customer) {

        Customer c = new Customer();
        c.setName(customer.getName());
        c.setEmail(customer.getEmail());
        c.setPhone(customer.getPhone());

        Address a = new Address();
        a.setCity(customer.getAddress().getCity());
        a.setCountry(customer.getAddress().getCountry());
        a.setPostalCode(customer.getAddress().getPostalCode());
        a.setState(customer.getAddress().getState());
        a.setStreet(customer.getAddress().getStreet());

        addressRepository.save(a);

        c.setAddress(a);

        repository.save(c);
        return "Customer Added";
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EntityModel<Customer> one (@PathVariable int id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(customer);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody Customer updateCustomer (@PathVariable int id, @RequestBody Customer newCustomer) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhone(newCustomer.getPhone());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteCustomer (@PathVariable int id) {
        repository.deleteById(id);
        return "Customer Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody CollectionModel<EntityModel<Customer>> all () {
        List<EntityModel<Customer>> customers = StreamSupport.stream(repository.findAll().spliterator(), false)
            .map(customer -> new EntityModel<>(customer,
                    linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
                    linkTo(methodOn(CustomerController.class).all()).withRel("Customers")))
            .collect(Collectors.toList());

        return new CollectionModel<>(customers,
                linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

}
