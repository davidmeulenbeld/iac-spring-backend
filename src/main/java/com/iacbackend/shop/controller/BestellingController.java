package com.iacbackend.shop.controller;

import com.iacbackend.shop.Assemblers.BestellingModelAssembler;
import com.iacbackend.shop.Exceptions.BestellingNotFoundException;
import com.iacbackend.shop.model.Bestelling;
import com.iacbackend.shop.model.Discount;
import com.iacbackend.shop.model.Product;
import com.iacbackend.shop.model.repository.BestellingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.iacbackend.shop.service.BestellingService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *
 */
@RestController
@RequestMapping(path="/bestelling")
public class BestellingController {

    @Autowired
    private BestellingModelAssembler assembler;

    @Autowired
    private BestellingRepository repository;

    @Autowired BestellingService service;

    @PostMapping()
    public @ResponseBody String addNewBestelling (@RequestBody Bestelling bestelling) {
        String message = service.addNewBestelling(bestelling);
        return message;
    }

    @GetMapping(path="/{id}")
    public @ResponseBody EntityModel<Bestelling> one (@PathVariable int id) {
        Bestelling bestelling = service.getBestelling(id);
        return assembler.toModel(bestelling);
    }

    @GetMapping(path = "/{id}/order")
    public @ResponseBody EntityModel<Bestelling> order (@PathVariable int id) {
        Bestelling bestelling = service.order(id);
        return assembler.toModel(bestelling);
    }

    @GetMapping(path = "/{id}/cancel")
    public @ResponseBody EntityModel<Bestelling> cancel (@PathVariable int id) {
        Bestelling bestelling = service.cancel(id);
        return assembler.toModel(bestelling);
    }

    @GetMapping(path="/all")
    public @ResponseBody CollectionModel<EntityModel<Bestelling>> all () {
        return service.all();
    }
}
