package org.uml.eshopas.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "cities")
public final class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<Address> addresses;

    public City() {}

    public City(String name, Collection<Address> addresses) {
        this.name = name;
        this.addresses = new ArrayList<>(addresses);
    }
}
