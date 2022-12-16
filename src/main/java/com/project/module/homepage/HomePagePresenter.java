package com.project.module.homepage;

import com.project.entity.Messages;
import com.project.entity.User;
import com.project.mvputil.BasePresenter;
import com.project.service.MessageService;
import com.project.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class HomePagePresenter extends BasePresenter<HomePageView> {

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    public List<User> getAllUser() {
        return userService.getUserList();
    }

    public User getUser(Integer id){
        return userService.getUser(id);
    }

    public void addUser(User user){
        userService.addUser(user);
    }

    public List<Messages> getAllMessages() {
        return messageService.getAllMessages();
    }

    public Messages getMessage(Integer id){
        return messageService.getMessage(id);
    }

    public void addMessage(Messages messages){
        messageService.addMessage(messages);
    }


}
