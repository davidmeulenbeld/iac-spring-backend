package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.ProductModelAssembler;
import com.iacbackend.shop.Exceptions.ProductNotFoundException;
import com.iacbackend.shop.model.Product;
import com.iacbackend.shop.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductModelAssembler assembler;

    @Autowired
    private ProductRepository repository;


    @PostMapping(path="/add")
    public @ResponseBody String addNewProduct (@RequestParam String name
            , @RequestParam String description
            , @RequestParam String image
            , @RequestParam Float price
            , @RequestParam int available ) {

        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setImage(image);
        p.setPrice(price);
        p.setAvailable(available);
        repository.save(p);
        return "Product Added";
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EntityModel<Product> one (@PathVariable int id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(product);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody Product updateProduct (@PathVariable int id, @RequestBody Product newProduct) {
        return repository.findById(id)
            .map(product -> {
                product.setName(newProduct.getName());
                product.setDescription(newProduct.getDescription());
                product.setImage(newProduct.getImage());
                product.setPrice(newProduct.getPrice());
                product.setAvailable(newProduct.getAvailable());
                return repository.save(product);
            })
            .orElseGet(() -> {
                newProduct.setId(id);
                return repository.save(newProduct);
            });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProduct (@PathVariable int id) {
        repository.deleteById(id);
        return "Product Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody CollectionModel<EntityModel<Product>> all () {

//        List<EntityModel<Product>> employees = repository.findAll().stream()
//                .map(assembler::toModel)
//                .collect(Collectors.toList());
//
//        return new CollectionModel<>(employees,
//                linkTo(methodOn(ProductController.class).all()).withSelfRel());

        List<EntityModel<Product>> employees = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(product -> new EntityModel<>(product,
                        linkTo(methodOn(ProductController.class).one(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).all()).withRel("products")))
                .collect(Collectors.toList());

        return new CollectionModel<>(employees,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }
}
