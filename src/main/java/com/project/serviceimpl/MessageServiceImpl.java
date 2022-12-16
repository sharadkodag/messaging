package com.project.serviceimpl;

import com.project.entity.Messages;
import com.project.repository.MessagesRepository;
import com.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessagesRepository messagesRepository;

    @Override
    public void addMessage(Messages message) {
        messagesRepository.save(message);
    }

    @Override
    public void removeMessage(Integer id) {
        messagesRepository.delete(Objects.requireNonNull(messagesRepository.findById(id).orElse(null)));
    }

    @Override
    public Messages getMessage(Integer id) {
        return messagesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }

}
