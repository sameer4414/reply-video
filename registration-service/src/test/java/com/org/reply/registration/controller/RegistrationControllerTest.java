package com.org.reply.registration.controller;

import com.org.reply.registration.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegistrationControllerTest {

    private static String baseUrl;
    private static URI uri;

    @BeforeEach
    public void setup() throws Exception{
        baseUrl = "http://localhost:8080/users/";
        uri = new URI(baseUrl);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegistrationController controller;

    @Test
    public void controllerLoads() throws Exception{
        assertThat(controller).isNotNull();
    }

    @Test
    public void validUserWithCreditCard() throws Exception {
        User user = User.builder().userName("replyHomeOffice").password("Valid123")
                .creditCardNumber(Optional.of("4659449930732585"))
                .dob("2000-06-20").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void validUserWithoutCreditCard() throws Exception {
        User user = User.builder().userName("reply").password("Valid@123").dob("2000-06-25").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void invalidUserWithoutPassword() throws Exception {
        User user = User.builder().userName("reply").dob("2000-06-25").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void invalidUserName() throws Exception {
        User user = User.builder().userName("user name with space").password("Valid@123").dob("2000-06-20").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void invalidEmailAddress() throws Exception {
        User user = User.builder().email("bad email address").userName("validUser").password("Valid@123").dob("2000-06-20").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void invalidPassword() throws Exception {
        User user = User.builder().password("invalid password").email("valid@email.com").userName("validUser").dob("2000-06-20").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void invalidDateOfBirth() throws Exception {
        User user = User.builder().dob("25062000").password("Valid@123").email("valid@email.com").userName("validUser").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void userAlreadyPresent() throws Exception {
        User user = User.builder().userName("reply").password("Valid123").creditCardNumber(Optional.of("1111111111111111"))
                .dob("2000-06-20").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void userIsNotAdult() throws Exception {
        User user = User.builder().userName("reply").password("Valid123").creditCardNumber(Optional.of("1111111111111111"))
                .dob("2020-06-20").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        result = this.restTemplate.postForEntity(uri, request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void userAddedButFilteredBasedOnCreditCard() throws Exception {
        User user = User.builder().userName("replyHomeOffice5").password("Valid123")
                .creditCardNumber(Optional.of("1111111111111111"))
                .dob("2000-06-20").email("asd@asd.com").build();

        HttpEntity<User> request = generateRequest(user);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);


        //2nd time
        User user1 = User.builder().userName("replyHomeOffice6").password("Valid123")
                .dob("2000-06-20").email("asd@asd.com").build();

        HttpEntity<User> request1 = generateRequest(user1);

        ResponseEntity<String> result1 = this.restTemplate.postForEntity(uri, request1, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI uri = new URI(UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("CreditCard", "Yes").toUriString());

        System.out.println(">>>>>>>> "+ this.restTemplate.getForEntity(uri, List.class).getBody().toString());
//        assertThat(this.restTemplate.getForEntity(uri, List.class).getBody().size()).isEqualTo(1);
    }

    private HttpEntity<User> generateRequest(User customer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(customer, headers);
    }

}
