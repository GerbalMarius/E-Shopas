package org.uml.eshopas.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Data
@Table(name = "carts")
public final class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @OneToOne(mappedBy = "cart")
    private Guest guest;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Order order;

    @OneToMany(mappedBy ="cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<CartProduct> cartProducts;


}
