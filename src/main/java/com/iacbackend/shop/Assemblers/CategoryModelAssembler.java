package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.CategoryController;
import com.iacbackend.shop.controller.ProductController;
import com.iacbackend.shop.model.Category;
import com.iacbackend.shop.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    @Override
    public EntityModel<Category> toModel(Category category) {

        Category newCategory = new Category();

        newCategory.setId(category.getId());
        newCategory.setImage(category.getImage());
        newCategory.setDescription(category.getDescription());
        newCategory.setName(category.getName());

        EntityModel<Category> categoryModel = new EntityModel<>(newCategory,
                linkTo(methodOn(CategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).all()).withRel("Categories"));

        if (category.getProducts() != null) {
            for (Product product: category.getProducts()) {
                categoryModel.add(
                        linkTo(methodOn(ProductController.class).one(product.getId())).withRel("Products")
                );
            }
        }

        return categoryModel;
    }
}
