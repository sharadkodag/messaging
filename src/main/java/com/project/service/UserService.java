package com.project.service;

import com.project.entity.Messages;
import com.project.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUserList();
    public User getUser(Integer id);
    public void addUser(User user);
    public void removeUser(Integer id);



}
