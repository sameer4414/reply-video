package com.org.reply.payment.controller;

import com.org.reply.payment.model.PaymentDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PaymentControllerTest {

    private static String baseUrl;
    private static URI uri;

    @BeforeAll
    public static void setup() throws Exception{
        baseUrl = "http://localhost:8181/payments/";
        uri = new URI(baseUrl);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentController controller;

    @Test
    public void controllerLoads() throws Exception{
        assertThat(controller).isNotNull();
    }

    @Test
    public void validPaymentDetails() throws Exception {
        PaymentDetails payment = PaymentDetails.builder().amount("123").creditCardNumber("4499407352036903").build();

        HttpEntity<PaymentDetails> request = generateRequest(payment);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void invalidCreditCard() throws Exception {
        PaymentDetails payment = PaymentDetails.builder().amount("123").creditCardNumber("1111").build();

        HttpEntity<PaymentDetails> request = generateRequest(payment);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void blankCreditCard() throws Exception {
        PaymentDetails payment = PaymentDetails.builder().amount("123").build();

        HttpEntity<PaymentDetails> request = generateRequest(payment);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void invalidAmount() throws Exception {
        PaymentDetails payment = PaymentDetails.builder().amount("1").creditCardNumber("1111111111111111").build();

        HttpEntity<PaymentDetails> request = generateRequest(payment);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void blankAmount() throws Exception {
        PaymentDetails payment = PaymentDetails.builder().amount("").creditCardNumber("1111111111111111").build();

        HttpEntity<PaymentDetails> request = generateRequest(payment);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private HttpEntity<PaymentDetails> generateRequest(PaymentDetails payment){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(payment, headers);
    }
}
