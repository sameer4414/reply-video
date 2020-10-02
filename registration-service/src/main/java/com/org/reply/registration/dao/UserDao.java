package com.org.reply.registration.dao;

import com.org.reply.registration.model.User;

import java.util.List;

public interface UserDao {
    boolean addUser(User user);
    List<User> getUsers();
}
