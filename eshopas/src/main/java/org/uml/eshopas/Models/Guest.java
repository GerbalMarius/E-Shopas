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

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public Guest() {}

    public Guest(String name, String lastName, String email, String telephoneNumber, Cart cart) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.cart = cart;
    }
}
