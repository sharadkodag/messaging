package com.project.module.homepage;

import com.project.entity.Messages;
import com.project.entity.User;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("view")
public class View extends VerticalLayout {

    @Autowired
    HomePagePresenter presenter;

    HorizontalLayout headingLayout;
    HorizontalLayout contactAndMessageLayout;
    VerticalLayout contactLayout;
    VerticalLayout messageLayout;
    VerticalLayout userMessagesLayout;
    Grid<User> userGrid;
    List<User> userList;
    List<Messages> messagesList;
    List<Messages> userMessages;
    List<MessageListItem> messageListItems;
    User user;
    MessageInput messageInput;
    Scroller scroller;

    @PostConstruct
    public void init(){

        setSizeFull();

        headingLayout = new HorizontalLayout();
        contactLayout = new VerticalLayout();
        messageLayout = new VerticalLayout();
        contactAndMessageLayout = new HorizontalLayout();
        userGrid = new Grid<>();
        userList = new ArrayList<>();
        userMessagesLayout = new VerticalLayout();
        messageListItems = new ArrayList<>();
        userMessages = new ArrayList<>();
//        user = new User();
        user = presenter.getUser(3);
        messageInput = new MessageInput();
        messagesList = new ArrayList<>();
        scroller = new Scroller();

        headingLayout.setWidthFull();
        contactLayout.setHeightFull();
        messageLayout.setSizeFull();
        contactLayout.setWidth(30, Unit.PERCENTAGE);
        messageLayout.setWidth(70, Unit.PERCENTAGE);
        contactAndMessageLayout.setSizeFull();
//        userMessagesLayout.setWidthFull();
//        userMessagesLayout.setMaxHeight(95, Unit.PERCENTAGE);
//        userMessagesLayout.setMinHeight(95, Unit.PERCENTAGE);
        userMessagesLayout.setSizeFull();
        messageInput.setWidthFull();
        scroller.setSizeFull();
//        userMessagesLayout.getStyle().set("overflow","scroll");
        userMessagesLayout.setJustifyContentMode(JustifyContentMode.END);

        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

//        user = (User) VaadinSession.getCurrent().getAttribute("User");

        contactAndMessageLayout.add(contactLayout, messageLayout);
        add(headingLayout, contactAndMessageLayout);
        addContact();
        addMessages();
    }

    public void addContact(){

        userList = presenter.getAllUser();
        userGrid.setItems(userList);
        Grid.Column<User> userColumn = userGrid.addColumn(u -> u.getFirstName() + " " + u.getLastName());

        contactLayout.add(userGrid);
    }

    public void addMessages(){

        userGrid.addSelectionListener(event -> {
            if (userGrid.getSelectedItems().size() > 0) {
                scroller.setContent(userMessagesLayout);
                messageLayout.add(scroller, messageInput);
                addUserMessage(event.getFirstSelectedItem().get());
            }else {
                messageLayout.removeAll();
            }
        });

        messageInput.addSubmitListener(event -> {
            VerticalLayout verticalLayout = new VerticalLayout();
            messagesList.add(new Messages(event.getValue(), LocalDateTime.now(), user, userGrid.getSelectedItems().iterator().next()));

            verticalLayout.setWidthFull();
            verticalLayout.setMargin(false);
            verticalLayout.setPadding(false);
            verticalLayout.setAlignItems(Alignment.END);

            MessageListItem messageListItem = new MessageListItem(event.getValue(), Instant.now(), user.getFirstName() + " " + user.getLastName());
            verticalLayout.add(new MessageList(messageListItem));
            userMessagesLayout.add(verticalLayout);
        });

    }

    public void addUserMessage(User user1){

        userMessagesLayout.removeAll();
//        messagesList.clear();
        userMessages.clear();
        messageListItems.clear();
        userMessages = messagesList.stream().filter(m -> m.getSender().getId().equals(user.getId()) && m.getReceiver().getId().equals(user1.getId())
                || m.getReceiver().getId().equals(user.getId()) && m.getSender().getId().equals(user1.getId())).collect(Collectors.toList());
        userMessages.forEach(m ->{
            VerticalLayout verticalLayout = new VerticalLayout();
            MessageListItem messageListItem = new MessageListItem(m.getMessage(), m.getDateTime().toInstant(ZoneOffset.UTC),
                    m.getSender().getFirstName() + " " + m.getSender().getLastName());
            if(m.getSender().getId().equals(user.getId())) {
                verticalLayout.setWidthFull();
                verticalLayout.setMargin(false);
                verticalLayout.setPadding(false);
                verticalLayout.setAlignItems(Alignment.END);
            }else {
                verticalLayout.setWidthFull();
                verticalLayout.setMargin(false);
                verticalLayout.setPadding(false);
            }

            verticalLayout.add(new MessageList(messageListItem));
            userMessagesLayout.add(verticalLayout);
        });;

//        for(int i=0; i<messageListItems.size(); i++){
//            if(userMessages.get(i).getId().equals(user.getId())){
//                VerticalLayout verticalLayout = new VerticalLayout();
//
//                verticalLayout.setWidthFull();
//                verticalLayout.setMargin(false);
//                verticalLayout.setPadding(false);
//                verticalLayout.setAlignItems(Alignment.END);
//
//                verticalLayout.add(new MessageList(messageListItems.get(i)));
//
//                userMessagesLayout.add(verticalLayout);
//            }else {
//                VerticalLayout verticalLayout = new VerticalLayout();
//
//                verticalLayout.setWidthFull();
//                verticalLayout.setMargin(false);
//                verticalLayout.setPadding(false);
//
//                userMessagesLayout.add(verticalLayout);
//            }
//        }

    }

}
