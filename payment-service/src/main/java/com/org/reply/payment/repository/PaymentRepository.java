package com.org.reply.payment.repository;

import com.org.reply.payment.model.PaymentDetails;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class PaymentRepository {
    private static final Set<PaymentDetails> paymentDetailsSet = new HashSet<>();

    public boolean addPaymentDetails(PaymentDetails paymentDetails){
        return paymentDetailsSet.add(paymentDetails);
    }

    public Set<PaymentDetails> getPaymentDetailsSet(){
        return paymentDetailsSet;
    }

}
