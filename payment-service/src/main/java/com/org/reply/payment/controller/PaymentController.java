package com.org.reply.payment.controller;

import com.org.reply.payment.model.PaymentDetails;
import com.org.reply.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentDetails> getPayments(){
        return paymentService.getPayments();
    }

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void payments(@Valid @RequestBody PaymentDetails paymentDetails){
        paymentService.pay(paymentDetails);
    }

}
