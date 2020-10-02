package com.org.reply.registration.service;

import com.org.reply.registration.dao.UserDao;
import com.org.reply.registration.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService{

    @Autowired
    private UserDao userDao;

    private HttpStatus httpStatus;

    @Override
    public ResponseEntity register(User user){
        //only if the user is adult would we proceed with adding him/her
        //if the user is already present in repository we raise a conflict status
        if (isAdult(user)){
            boolean isAdded = userDao.addUser(user);
            httpStatus = isAdded ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        }else{
            httpStatus = HttpStatus.FORBIDDEN;//user is not an adult
        }
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    private boolean isAdult(User user){
        return ChronoUnit.YEARS.between(LocalDate.parse(user.getDob().trim()), LocalDate.now()) >= 18;
    }
}
