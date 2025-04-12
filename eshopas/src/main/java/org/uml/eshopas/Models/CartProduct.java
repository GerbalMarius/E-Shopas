package org.uml.eshopas.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "cart_products")
public class CartProduct {


    @EmbeddedId
    private CartProductId cartProductId = new CartProductId();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;
}
