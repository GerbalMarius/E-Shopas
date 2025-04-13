package org.uml.eshopas.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "manufacturers")
public final class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<Product> products;
}
