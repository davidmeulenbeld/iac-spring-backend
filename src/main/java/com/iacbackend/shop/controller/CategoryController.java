package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.CategoryModelAssembler;
import com.iacbackend.shop.Exceptions.CategoryNotFoundException;
import com.iacbackend.shop.Exceptions.ProductNotFoundException;
import com.iacbackend.shop.model.Category;
import com.iacbackend.shop.model.Customer;
import com.iacbackend.shop.model.Product;
import com.iacbackend.shop.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(path="/category")
public class CategoryController {

    @Autowired
    private CategoryModelAssembler assembler;

    @Autowired
    private CategoryRepository repository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String name
            , @RequestParam String description
            , @RequestParam String image ) {

        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        c.setImage(image);
        repository.save(c);
        return "Category Added";
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EntityModel<Category> one (@PathVariable int id) {
        Category category = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(category);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody Category updateCategory (@PathVariable int id, @RequestBody Category newCategory) {
        return repository.findById(id)
            .map(product -> {
                product.setName(newCategory.getName());
                product.setDescription(newCategory.getDescription());
                product.setImage(newCategory.getImage());
                return repository.save(product);
            })
            .orElseGet(() -> {
                newCategory.setId(id);
                return repository.save(newCategory);
            });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteCategory (@PathVariable int id) {
        repository.deleteById(id);
        return "Category Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    CollectionModel<EntityModel<Category>> all () {
        List<EntityModel<Category>> categories = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(category -> new EntityModel<>(category,
                        linkTo(methodOn(CategoryController.class).one(category.getId())).withSelfRel(),
                        linkTo(methodOn(CategoryController.class).all()).withRel("Categories")))
                .collect(Collectors.toList());

        return new CollectionModel<>(categories,
                linkTo(methodOn(CategoryController.class).all()).withSelfRel());
    }
}
