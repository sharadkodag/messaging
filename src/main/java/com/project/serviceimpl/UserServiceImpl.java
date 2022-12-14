package com.project.serviceimpl;

import com.project.entity.Messages;
import com.project.entity.User;
import com.project.repository.MessagesRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessagesRepository messagesRepository;

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }


}
