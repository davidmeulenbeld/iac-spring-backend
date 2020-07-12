package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.BestellingController;
import com.iacbackend.shop.controller.CategoryController;
import com.iacbackend.shop.controller.CustomerController;
import com.iacbackend.shop.controller.ProductController;
import com.iacbackend.shop.model.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BestellingModelAssembler implements RepresentationModelAssembler<Bestelling, EntityModel<Bestelling>> {
    @Override
    public EntityModel<Bestelling> toModel(Bestelling bestelling) {

        Bestelling newBestelling = new Bestelling();

        newBestelling.setId(bestelling.getId());

        if (bestelling.getOrdered()) {
            if (bestelling.getProducts() != null) {
                float totalPrice = 0;
                for (Product product: bestelling.getProducts()) {
                    if (product.getDiscount() != null) {
                        totalPrice = totalPrice + ((product.getPrice() / 100) * (100 - product.getDiscount().getPercentage()));
                    } else {
                        totalPrice = totalPrice + product.getPrice();
                    }
                }
                newBestelling.setTotalPrice(totalPrice);
            } else {
                newBestelling.setTotalPrice((float) 0);
            }
            newBestelling.setOrderDate(new Date());
        } else {
            newBestelling.setTotalPrice(bestelling.getTotalPrice());
            newBestelling.setOrderDate(bestelling.getOrderDate());
        }



        EntityModel<Bestelling> bestellingModel = new EntityModel<>(newBestelling,
                linkTo(methodOn(BestellingController.class).one(newBestelling.getId())).withSelfRel(),
                linkTo(methodOn(BestellingController.class).all()).withRel("Bestellingen"));

        if (bestelling.getProducts() != null) {
            for (Product product: bestelling.getProducts()) {
                bestellingModel.add(
                        linkTo(methodOn(ProductController.class).one(product.getId())).withRel("Products")
                );
            }
        }

        if (bestelling.getCustomer() != null) {
            bestellingModel.add(
                    linkTo(methodOn(CustomerController.class).one(bestelling.getCustomer().getId())).withRel("Customer")
            );
        }

        // Check if bestelling is ordered
        if (bestelling.getOrdered()) {
            bestellingModel.add(
                    linkTo(methodOn(BestellingController.class).cancel(bestelling.getId())).withRel("Cancel Bestelling")
            );
        } else {
            bestellingModel.add(
                    linkTo(methodOn(BestellingController.class).order(bestelling.getId())).withRel("Order Bestelling")
            );
        }

        return bestellingModel;
    }
}
