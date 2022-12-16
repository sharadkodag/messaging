package com.project.service;

import com.project.entity.Messages;

import java.util.List;

public interface MessageService {

    public void addMessage(Messages message);
    public void removeMessage(Integer id);
    public Messages getMessage(Integer id);
    public List<Messages> getAllMessages();

}
