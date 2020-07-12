package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.BestellingController;
import com.iacbackend.shop.controller.CategoryController;
import com.iacbackend.shop.controller.CustomerController;
import com.iacbackend.shop.model.Bestelling;
import com.iacbackend.shop.model.Category;
import com.iacbackend.shop.model.Customer;
import com.iacbackend.shop.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    @Override
    public EntityModel<Customer> toModel(Customer customer) {

        Customer newCustomer = new Customer();

        newCustomer.setId(customer.getId());
        newCustomer.setName(customer.getName());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setEmail(customer.getEmail());

        EntityModel<Customer> customerModel = new EntityModel<>(newCustomer,
                linkTo(methodOn(CustomerController.class).one(newCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).all()).withRel("Customers")
        );

        if (customer.getBestellingen() != null) {
            for (Bestelling bestelling: customer.getBestellingen()) {
                customerModel.add(
                        linkTo(methodOn(BestellingController.class).one(bestelling.getId())).withRel("Bestellingen")
                );
            }
        }

        return customerModel;
    }
}
