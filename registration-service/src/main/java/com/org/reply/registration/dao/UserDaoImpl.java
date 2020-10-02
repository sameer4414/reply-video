package com.org.reply.registration.dao;

import com.org.reply.registration.model.User;
import com.org.reply.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean addUser(User user){
        return userRepository.addUser(user);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userRepository.getUsers());
    }
}
