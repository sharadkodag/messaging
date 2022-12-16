package com.project.module.homepage;

import com.project.entity.Messages;
import com.project.entity.User;
import com.project.mvputil.BaseView;
import com.vaadin.componentfactory.Chat;
import com.vaadin.componentfactory.model.Message;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
@Route("home")
public class HomePageView extends BaseView<HomePagePresenter> {

    @Autowired
    HomePagePresenter homePagePresenter;
    User user;
    List<Messages> allMessages;
    List<Message> messageList1 ;
    HorizontalLayout horizontalLayout;
    VerticalLayout verticalLayout1;
    VerticalLayout verticalLayout2;
    TextField textField = new TextField();
    Grid<User> userGrid = new Grid<>();

    @Override
    protected void init() {

        horizontalLayout = new HorizontalLayout();
        verticalLayout1 = new VerticalLayout();
        verticalLayout2 = new VerticalLayout();

        VaadinSession.getCurrent().setAttribute("User", homePagePresenter.getUser(1));
        user = (User)VaadinSession.getCurrent().getAttribute("User");
        allMessages = homePagePresenter.getAllMessages();
        messageList1 = new ArrayList<>();
        if(user!=null) {
            System.out.println(user.getFirstName());
        }

        setPadding(false);
        setMargin(false);
        setSizeFull();
        horizontalLayout.setSizeFull();
        verticalLayout1.setHeightFull();
        verticalLayout1.setWidth(25, Unit.PERCENTAGE);
        verticalLayout2.setSizeFull();

        add(addHeading());
        add(addContactsLayout());
    }

    public HorizontalLayout addHeading(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label label = new Label("facebook");

        horizontalLayout.setWidthFull();
        label.getStyle().set("color","white").set("font-weight","bold").set("font-size","300%");
        horizontalLayout.getStyle().set("background-color","blue");

        horizontalLayout.add(label);

        return horizontalLayout;
    }

    public HorizontalLayout addContactsLayout(){

        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.setWidthFull();
        textField.setPlaceholder("Search");

        List<User> userList = homePagePresenter.getAllUser();
        userGrid.setItems(userList);
        Grid.Column<User> userColumn = userGrid.addColumn(user -> user.getFirstName() + " " + user.getLastName()).setHeader("Contacts");

        textField.addValueChangeListener(event -> {
            ListDataProvider<User> dataProvider = (ListDataProvider) userGrid.getDataProvider();
            dataProvider.addFilter(e -> (e.getFirstName().toLowerCase() + " " + e.getLastName().toLowerCase()).contains(textField.getValue().toLowerCase()));
        });

//        List<Message> messageList = allMessages.stream().filter(m -> m.getSender().getId().equals(user.getId()) || m.getReceiver().getId().equals(user.getId()))
//                .map(m -> new Message(m.getMessage(),m.getSender().getFirstName(),
//                        m.getSender().getFirstName() + " " + m.getSender().getLastName(), m.getReceiver().getId().equals(4))).collect(Collectors.toList());

//        Chat chat = new Chat();
//        chat.getElement().getStyle().set("width","100%").set("height","100%");
//
//        chat.scrollToBottom();
//        chat.setDebouncePeriod(200);
//        chat.setLazyLoadTriggerOffset(1000);
//

//        userGrid.addSelectionListener(event -> {
//            verticalLayout2.add(chat);
//            messageList1.clear();
//            allMessages.clear();
//            chat.getMessages().clear();
//            allMessages = homePagePresenter.getAllMessages();
//            messageList1 = allMessages.stream().filter(m -> m.getSender().getId().equals(user.getId()) && m.getReceiver().getId().equals(event.getFirstSelectedItem().orElse(null).getId()))
//                    .map(m -> new Message(m.getMessage(),m.getSender().getFirstName(),
//                            m.getSender().getFirstName() + " " + m.getSender().getLastName(), m.getReceiver().getId().equals(event.getFirstSelectedItem().orElse(null).getId()))).collect(Collectors.toList());
//            chat.setMessages(messageList1);
//        });

//        chat.addLazyLoadTriggerEvent(event -> {
//
//        });
//        chat.addChatNewMessageListener(evt -> {
//            Message message = new Message(evt.getMessage(), user.getFirstName(), user.getFirstName() + " " + user.getLastName(), true);
//            messageList1.add(message);
//            Messages messages = new Messages(evt.getMessage(), LocalDateTime.now(), user,
//                    userGrid.getSelectedItems().iterator().next());
//            homePagePresenter.addMessage(messages);
//            allMessages.add(messages);
////            chat.addNewMessage(new Message(event.getMessage(), "SK", "Sharad Kodag", true));
////            chat.addNewMessage(message);
//            chat.setMessages(messageList1);
//            evt.getSource().clearInput();
//            chat.scrollToBottom();
//        });

//        messageList.add(new Message("Hi", "SK", "Sharad Kodag", true));
//        messageList.add(new Message("Hi", "VN", "Vishal Naik", false));
//        messageList.add(new Message("Hi", "SB", "Shivani Bansal", false));
//        messageList.add(new Message("Hi", "SK", "Ganesh Sangle", false));
//        messageList.add(new Message("Hi", "WS", "Waseem Shaikh", false));

//        for(Messages messages : allMessages){
//            if(messages.getSender().getId().equals(4) || messages.getReceiver().equals(4))
//            messageList.add(new Message(messages.getMessage(), String.valueOf(messages.getReceiver().getFirstName().charAt(0)) + messages.getReceiver().getLastName().charAt(0),
//                    messages.getSender().getFirstName() + " " + messages.getSender().getLastName(),messages.getSender().getId().equals(4) ));
//        }

        verticalLayout1.add(textField, userGrid);
        horizontalLayout.add(verticalLayout1, verticalLayout2);
        return horizontalLayout;
    }
}