package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.CustomerController;
import com.iacbackend.shop.model.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        return new EntityModel<>(customer,
                linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).all()).withRel("Customers"));
    }
}
