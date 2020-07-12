package com.iacbackend.shop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String image;

    private Float price;

    private Integer available;

    @OneToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_bestelling",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bestelling_id", referencedColumnName = "id"))
    private List<Bestelling> bestellingen;

    @JsonSetter
    public void setBestellingen(List<Bestelling> bestellingen) { this.bestellingen = bestellingen; }

    @JsonIgnore
    public List<Bestelling> getBestellingen() { return bestellingen; }

    @JsonSetter
    public void setCategories(List<Category> categories) { this.categories = categories; }

    @JsonIgnore
    public List<Category> getCategories() { return categories; }

    @JsonSetter
    public void setDiscount(Discount discount) { this.discount = discount; }

    @JsonIgnore
    public Discount getDiscount() { return discount; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }


}

