package com.org.reply.registration.repository;

import com.org.reply.registration.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepository {
    private Set<User> userSet = new HashSet<>();

    public boolean addUser(User user){
        return userSet.add(user);
    }

    public Set<User> getUsers(){
        return userSet;
    }

}