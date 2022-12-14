package com.project.module.login;

import com.project.entity.User;
import com.project.mvputil.BasePresenter;
import com.project.mvputil.BaseView;
import com.project.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class FacebookPresenter extends BasePresenter<BaseView> {
    @Autowired
    UserService userService;
    public List<User> getUserList(){
        return userService.getUserList();
    }

    public void addUser(User user){
        userService.addUser(user);
    }

}
