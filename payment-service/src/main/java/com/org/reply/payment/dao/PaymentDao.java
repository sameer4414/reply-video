package com.org.reply.payment.dao;

import com.org.reply.payment.model.PaymentDetails;

import java.util.List;

public interface PaymentDao {
    boolean addPaymentDetails(PaymentDetails customer);
    List<PaymentDetails> getPaymentDetails();
}
