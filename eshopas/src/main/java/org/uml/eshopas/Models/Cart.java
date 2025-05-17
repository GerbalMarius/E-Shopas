package org.uml.eshopas.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "carts")
public final class Cart {

    public  Cart(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Guest guest;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Order order;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Collection<CartProduct> cartProducts = new ArrayList<>();
}
