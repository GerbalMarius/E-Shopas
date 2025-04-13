package org.uml.eshopas.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "guests")
public final class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(nullable = false, name = "telephone_number")
    private String telephoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
}
