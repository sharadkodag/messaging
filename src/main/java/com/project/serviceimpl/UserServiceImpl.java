package com.project.serviceimpl;

import com.project.entity.Messages;
import com.project.entity.User;
import com.project.repository.MessagesRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessagesRepository messagesRepository;

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeUser(Integer id) {

        userRepository.delete(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }


}
