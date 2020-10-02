package com.org.reply.payment.service;

import com.org.reply.payment.model.PaymentDetails;

import java.util.List;

public interface PaymentService {
    boolean pay(PaymentDetails paymentDetails);
    List<PaymentDetails> getPayments();
}
