package com.project.service;

import com.project.entity.Messages;
import com.project.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getUserList();
    public void addUser(User user);

    public List<Messages> getAllMessages();

}
