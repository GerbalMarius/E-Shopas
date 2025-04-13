package org.uml.eshopas.Models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "addresses")
public final class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "deliveryAddress")
    private Collection<Order> orders;

    public Address() {}

    public Address(String street, String houseNumber, City city, Collection<Order> orders) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.orders = new ArrayList<>(orders);
    }
}
