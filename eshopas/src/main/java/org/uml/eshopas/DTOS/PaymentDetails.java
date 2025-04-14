package org.uml.eshopas.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.uml.eshopas.Models.DeliveryMethod;

public record PaymentDetails(

        @NotEmpty(message = "Name must not be empty")
        String firstName,

        @NotEmpty(message = "Last name must not be empty")
        String lastName,

        @NotEmpty(message = "Email must not be empty")
        @Email(regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$",
                message = "Email must in valid format")
        String email,

        @NotEmpty(message = "Phone must not be empty")
        String phone,

        @NotEmpty(message = "Street must not be empty")
        String street,

        @NotEmpty(message = "House number must not be empty")
        String houseNumber,

        @NotEmpty(message = "City name must not be empty")
        String cityName,

        @NotNull(message = "Delivery method must not be null")
        DeliveryMethod deliveryMethod,

        String paymentIntentId
) {
}
