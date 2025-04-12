package org.uml.eshopas.Models;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public final class CartProductId implements Serializable {

    private long cartId;
    private long productId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CartProductId that)) return false;
        return cartId == that.cartId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
