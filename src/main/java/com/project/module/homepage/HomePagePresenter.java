package com.project.module.homepage;

import com.project.entity.Messages;
import com.project.entity.User;
import com.project.mvputil.BasePresenter;
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

    public List<User> getAllUser() {
        return userService.getUserList();
    }

    public List<Messages> getAllMessages() {
        return userService.getAllMessages();
    }

}
