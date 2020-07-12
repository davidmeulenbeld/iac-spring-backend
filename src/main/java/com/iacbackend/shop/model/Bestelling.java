package com.iacbackend.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Bestelling {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Date orderDate;

    private Float totalPrice;

    private boolean ordered;

    @ManyToMany(mappedBy = "bestellingen")
    private List<Product> products;

    @JsonSetter
    public List<Product> getProducts() { return products; }

    @JsonIgnore
    public void setProducts(List<Product> products) { this.products = products; }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonSetter
    public void setOrdered(boolean ordered) { this.ordered = ordered; }

    @JsonIgnore
    public boolean getOrdered() { return ordered; }

    @JsonIgnore
    public Customer getCustomer() { return customer; }

    @JsonSetter
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

