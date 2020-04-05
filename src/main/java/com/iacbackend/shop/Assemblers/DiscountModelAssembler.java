package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.DiscountController;
import com.iacbackend.shop.controller.ProductController;
import com.iacbackend.shop.model.Discount;
import com.iacbackend.shop.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DiscountModelAssembler implements RepresentationModelAssembler<Discount, EntityModel<Discount>> {
    @Override
    public EntityModel<Discount> toModel(Discount discount) {

        Discount newDiscount = new Discount();

        newDiscount.setStartDate(discount.getStartDate());
        newDiscount.setEndDate(discount.getEndDate());
        newDiscount.setText(discount.getText());
        newDiscount.setPercentage(discount.getPercentage());

        EntityModel<Discount> discountModel = new EntityModel<>(newDiscount,
            linkTo(methodOn(DiscountController.class).one(discount.getId())).withSelfRel(),
            linkTo(methodOn(DiscountController.class).all()).withRel("Discounts"));

        discountModel.add(linkTo(methodOn(ProductController.class).one(discount.getProduct().getId())).withRel("Product"));

        return discountModel;
    }
}