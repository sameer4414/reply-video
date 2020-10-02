package com.org.reply.registration.service;

import com.org.reply.registration.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegistrationService {
    ResponseEntity<User> register(User customer);
    List<User> getUsers();
}
