package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.DiscountModelAssembler;
import com.iacbackend.shop.Exceptions.DiscountNotFoundException;
import com.iacbackend.shop.model.Discount;
import com.iacbackend.shop.model.repository.DiscountRepository;
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
@RequestMapping(path = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountModelAssembler assembler;

    @Autowired
    private DiscountRepository repository;


    @PostMapping(path="/add")
    public @ResponseBody String addNewDiscount (@RequestBody Discount discount) {

        Discount d = new Discount();
        d.setStartDate(discount.getStartDate());
        d.setEndDate(discount.getEndDate());
        d.setPercentage(discount.getPercentage());
        d.setText(discount.getText());
        d.setProduct(discount.getProduct());

        repository.updateRelation(discount.getId(), d.getProduct().getId());

        repository.save(d);
        return "Discount Added";
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EntityModel<Discount> one (@PathVariable int id) {
        Discount discount = repository.findById(id).orElseThrow(() -> new DiscountNotFoundException(id));

        return assembler.toModel(discount);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody Discount updateDiscount (@PathVariable int id, @RequestBody Discount newDiscount) {
        return repository.findById(id)
                .map(discount -> {
                    discount.setStartDate(newDiscount.getStartDate());
                    discount.setEndDate(newDiscount.getEndDate());
                    discount.setPercentage(newDiscount.getPercentage());
                    discount.setText(newDiscount.getText());
                    discount.setProduct(newDiscount.getProduct());
                    return repository.save(discount);
                })
                .orElseGet(() -> {
                    newDiscount.setId(id);
                    return repository.save(newDiscount);
                });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteDiscount (@PathVariable int id) {
        repository.deleteById(id);
        return "Discount Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody CollectionModel<EntityModel<Discount>> all () {
        List<EntityModel<Discount>> discounts = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(product -> new EntityModel<>(product,
                        linkTo(methodOn(DiscountController.class).one(product.getId())).withSelfRel(),
                        linkTo(methodOn(DiscountController.class).all()).withRel("Discounts")))
                .collect(Collectors.toList());

        return new CollectionModel<>(discounts,
                linkTo(methodOn(DiscountController.class).all()).withSelfRel());
    }
}
