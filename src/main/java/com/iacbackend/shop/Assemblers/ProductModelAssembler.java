package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.DiscountController;
import com.iacbackend.shop.controller.ProductController;
import com.iacbackend.shop.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {

        Product newProduct = new Product();

        newProduct.setId(product.getId());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setAvailable(product.getAvailable());
        newProduct.setImage(product.getImage());
        newProduct.setDescription(product.getDescription());

        EntityModel<Product> productModel =  new EntityModel<>(newProduct,
                linkTo(methodOn(ProductController.class).one(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("Products"));

        if (product.getDiscount() != null) {
            productModel.add(
                    linkTo(methodOn(DiscountController.class).one(product.getDiscount().getId())).withRel("Discount")
            );
        }

        return productModel;
    }
}
