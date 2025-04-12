package org.uml.eshopas.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record PaymentDetails(

        @NotEmpty(message = "Name must not be empty.")
        String name,

        @NotEmpty(message = "Email must not be empty")
        @Email(regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$",
                message = "Email must in valid format.")
        String email,

        @NotEmpty(message = "Phone must not be empty.")
        String phone,

        @NotEmpty(message = "Address must not be empty.")
        String address,

        String paymentIntentId
) {
}
