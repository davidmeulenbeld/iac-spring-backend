package com.iacbackend.shop.service;

import com.iacbackend.shop.Assemblers.BestellingModelAssembler;
import com.iacbackend.shop.Exceptions.BestellingNotFoundException;
import com.iacbackend.shop.controller.BestellingController;
import com.iacbackend.shop.model.Bestelling;
import com.iacbackend.shop.model.repository.BestellingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BestellingService {

    @Autowired
    private BestellingRepository repository;

    @Autowired
    private BestellingModelAssembler assembler;

    public String addNewBestelling (Bestelling bestelling) {
        Bestelling b = new Bestelling();
        b.setCustomer(bestelling.getCustomer());
        repository.save(b);
        return "Bestelling added";
    }

    public Bestelling getBestelling(int id) {
        Bestelling bestelling = repository.findById(id).orElseThrow(() -> new BestellingNotFoundException(id));
        return bestelling;
    }

    public Bestelling order(int id) {
        Bestelling bestelling = repository.findById(id).orElseThrow(() -> new BestellingNotFoundException(id));
        repository.order(id);
        return bestelling;
    }

    public Bestelling cancel(int id) {
        Bestelling bestelling = repository.findById(id).orElseThrow(() -> new BestellingNotFoundException(id));

        repository.cancel(id);
        return bestelling;
    }

    public CollectionModel<EntityModel<Bestelling>> all() {
        List<EntityModel<Bestelling>> bestellingen = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(category -> new EntityModel<>(category,
                        linkTo(methodOn(BestellingController.class).one(category.getId())).withSelfRel(),
                        linkTo(methodOn(BestellingController.class).all()).withRel("Categories")))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Bestelling>> bestellingenCollectionModel = new CollectionModel<>(bestellingen,
                linkTo(methodOn(BestellingController.class).all()).withSelfRel());
        return bestellingenCollectionModel;
    }



}
