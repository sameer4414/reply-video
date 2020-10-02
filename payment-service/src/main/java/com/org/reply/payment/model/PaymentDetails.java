package com.org.reply.payment.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class PaymentDetails {
    @NotEmpty
    @Pattern(regexp = "(\\D*\\d){16}", message = "The credit card number should be of size 16")
    private String creditCardNumber;

    @NotEmpty
    @Pattern(regexp = "(\\D*\\d){3}", message = "The amount should be of size 3")
    private String amount;

}
