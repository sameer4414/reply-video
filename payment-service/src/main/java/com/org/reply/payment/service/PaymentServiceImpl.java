package com.org.reply.payment.service;

import com.org.reply.payment.dao.PaymentDao;
import com.org.reply.payment.model.PaymentDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentDao paymentDao;

    @Override
    public boolean pay(PaymentDetails paymentDetails){
        return paymentDao.addPaymentDetails(paymentDetails);
    }

    @Override
    public List<PaymentDetails> getPayments() {
        return paymentDao.getPaymentDetails();
    }
}
