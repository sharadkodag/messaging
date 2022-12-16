package com.project.module.homepage;

import com.project.entity.Messages;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Route("chat")
public class Chat extends VerticalLayout {

    @Autowired
    HomePagePresenter homePagePresenter;

    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout v2 = new VerticalLayout();
    List<Messages> messagesList  = homePagePresenter.getAllMessages();
    List<MessageListItem> messageListItems = new ArrayList<>();
    MessageList messageList  =new MessageList();
    MessageListItem messageListItem = new MessageListItem();
    MessageInput messageInput = new MessageInput();

    static int i =0;

    @PostConstruct
    public void init(){

        setSizeFull();

        verticalLayout.setSizeFull();
        v2.setWidthFull();
        messageList.setSizeFull();
        messageInput.setWidthFull();

        messageInput.addSubmitListener(event -> {

            if(i%3==0){

                MessageListItem messageListItem1 = new MessageListItem(event.getValue(), LocalDateTime.now().toInstant(ZoneOffset.UTC), "Sharad Kodag");
                MessageList messageList1 = new MessageList();
                messageList1.setItems(messageListItem1);
                VerticalLayout v2 = new VerticalLayout();
                v2.setMargin(false);
                v2.setPadding(false);
                v2.setAlignItems(Alignment.END);
                v2.add(messageList1);
                verticalLayout.add(v2);
            }else {
                MessageListItem messageListItem1 = new MessageListItem(event.getValue(), LocalDateTime.now().toInstant(ZoneOffset.UTC), "Sharad Kodag");
                MessageList messageList1 = new MessageList();
                messageList1.setItems(messageListItem1);
                VerticalLayout v3 = new VerticalLayout();
                v3.setMargin(false);
                v3.setPadding(false);
                v3.add(messageList1);
                verticalLayout.add(v3);
            }
            i++;
        });

        add(messageInput,verticalLayout);

    }
}
