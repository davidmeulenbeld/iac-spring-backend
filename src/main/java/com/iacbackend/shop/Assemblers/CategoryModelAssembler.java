package com.iacbackend.shop.Assemblers;

import com.iacbackend.shop.controller.CategoryController;
import com.iacbackend.shop.model.Category;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    @Override
    public EntityModel<Category> toModel(Category category) {
        return new EntityModel<>(category,
                linkTo(methodOn(CategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).all()).withRel("Categories"));
    }
}
