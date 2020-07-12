package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.CategoryController;
import com.iacbackend.shop.controller.DiscountController;
import com.iacbackend.shop.controller.ProductController;
import com.iacbackend.shop.model.Category;
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

        if (product.getDiscount() != null) {
            newProduct.setPrice((product.getPrice() / 100) * (100 - product.getDiscount().getPercentage()));
        } else {
            newProduct.setPrice((product.getPrice()));
        }

        newProduct.setAvailable(product.getAvailable());
        newProduct.setImage(product.getImage());
        newProduct.setDescription(product.getDescription());
//        newProduct.setDiscount(product.getDiscount());
//        newProduct.setCategories(product.getCategories());

        EntityModel<Product> productModel = new EntityModel<>(newProduct,
                linkTo(methodOn(ProductController.class).one(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("Products"),
                // Link to method to add to bestelling 38 of customer 22, since there is no authorization module this has to do
                linkTo(methodOn(ProductController.class).addToBestelling(product.getId(), 38)).withRel("Voeg toe aan bestelling")
        );

        if (product.getDiscount() != null) {
            productModel.add(
                    linkTo(methodOn(DiscountController.class).one(product.getDiscount().getId())).withRel("Discount")
            );
        }

        if (product.getCategories() != null) {
            for (Category category: product.getCategories()) {
                productModel.add(
                        linkTo(methodOn(CategoryController.class).one(category.getId())).withRel("Categories")
                );
            }
        }

        return productModel;
    }
}
