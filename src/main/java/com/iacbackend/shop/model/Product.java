package com.iacbackend.shop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String image;

    private Float price;

    private Integer available;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "product_discount",
        joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id") },
        inverseJoinColumns = {
            @JoinColumn(name = "discount_id", referencedColumnName = "id")
        })
    private Discount discount;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "product_bestelling",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Bestelling> orders;

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

