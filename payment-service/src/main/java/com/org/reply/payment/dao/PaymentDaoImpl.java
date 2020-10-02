package com.org.reply.payment.dao;

import com.org.reply.payment.model.PaymentDetails;
import com.org.reply.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao{

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public boolean addPaymentDetails(PaymentDetails paymentDetails){
        return paymentRepository.addPaymentDetails(paymentDetails);
    }

    @Override
    public List<PaymentDetails> getPaymentDetails() {
        return new ArrayList<>(paymentRepository.getPaymentDetailsSet());
    }
}
