package com.org.reply.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@Data
@Builder
public class User {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid username")
    @EqualsAndHashCode.Include
    private String userName;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = "Invalid password")
    @EqualsAndHashCode.Exclude
    private String password;

    @NotEmpty
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email address")
    @EqualsAndHashCode.Exclude
    private String email;

    @NotEmpty
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Invalid Date of Birth")
    @EqualsAndHashCode.Exclude
    private String dob;

    private Optional<@Pattern(regexp = "(\\D*\\d){16}") String> creditCardNumber;

}
