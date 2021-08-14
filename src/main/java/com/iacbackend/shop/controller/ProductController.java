package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.BestellingModelAssembler;
import com.iacbackend.shop.Assemblers.ProductModelAssembler;
import com.iacbackend.shop.Exceptions.BestellingNotFoundException;
import com.iacbackend.shop.Exceptions.ProductNotFoundException;
import com.iacbackend.shop.model.Bestelling;
import com.iacbackend.shop.model.Category;
import com.iacbackend.shop.model.Product;
import com.iacbackend.shop.model.repository.BestellingRepository;
import com.iacbackend.shop.model.repository.DiscountRepository;
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

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductModelAssembler assembler;

    @Autowired
    private BestellingModelAssembler bestellingAssembler;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private BestellingRepository bestellingRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @PostMapping()
    public @ResponseBody String addNewProduct (@RequestBody Product product) {

        // Create product object and save it
        Product p = new Product();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setImage(product.getImage());
        p.setPrice(product.getPrice());
        p.setAvailable(product.getAvailable());

        repository.save(p);

        // Save this product object into the category "Nieuw"
        repository.addCategory(p.getId(),25);

        return "Product " + p.getId() + " Has been added";
    }

    @GetMapping(path = "/{P_Id}/addToBestelling/{B_Id}")
    public @ResponseBody EntityModel<Bestelling> addToBestelling (@PathVariable int P_Id, @PathVariable int B_Id) {
        Bestelling bestelling = bestellingRepository.findById(B_Id).orElseThrow(() -> new BestellingNotFoundException(B_Id));

        Product product = repository.findById(P_Id).orElseThrow(() -> new ProductNotFoundException(P_Id));

        repository.addToBestelling(product.getId(), bestelling.getId());

        repository.changeStorage(product.getId(), (product.getAvailable() - 1));

        return bestellingAssembler.toModel(bestelling);
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
                product.setCategories(newProduct.getCategories());
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
        List<EntityModel<Product>> products = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(product -> new EntityModel<>(product,
                        linkTo(methodOn(ProductController.class).one(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).all()).withRel("products")))
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }
}
