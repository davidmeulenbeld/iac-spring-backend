package com.iacbackend.shop.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Date startdate;

    private Date enddate;

    private Integer percentage;

    @ManyToMany
    private Set<Product> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startdate;
    }

    public void setStartDate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEndDate() {
        return enddate;
    }

    public void setEndDate(Date endDate) {
            this.enddate = endDate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }


}

