package org.uml.eshopas.DTOS;

import org.uml.eshopas.Models.DeliveryMethod;
import org.uml.eshopas.Models.OrderStatus;

import java.time.LocalDateTime;

public record OrderData(
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        DeliveryMethod deliveryMethod,
        CartData cartData
) {
    public OrderData {
       cartData = new CartData(cartData);
    }
}
