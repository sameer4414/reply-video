package com.org.reply.registration.controller;

import com.org.reply.registration.model.User;
import com.org.reply.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(@RequestParam(name = "CreditCard", defaultValue = "No") String creditCard){
        if (creditCard.equals("Yes")){
            return registrationService.getUsers().stream().filter(c -> c.getCreditCardNumber().isPresent()).collect(Collectors.toList());
        }
        return registrationService.getUsers();
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postMessage(@Valid @RequestBody User user){
        return registrationService.register(user);
    }
}
