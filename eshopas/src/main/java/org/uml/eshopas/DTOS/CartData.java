package org.uml.eshopas.DTOS;

import java.math.BigDecimal;
import java.util.List;

public record CartData(
        BigDecimal totalPrice,
        List<ProductData> products
) {
    public CartData {
        totalPrice = new BigDecimal(totalPrice.toString());
        products = List.copyOf(products);
    }

    public CartData(CartData cartData){
        this(cartData.totalPrice, cartData.products);
    }
}
