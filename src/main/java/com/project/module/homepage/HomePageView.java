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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
@Route("home")
public class HomePageView extends BaseView<HomePagePresenter> {

    @Autowired
    HomePagePresenter homePagePresenter;

    @Override
    protected void init() {
        setPadding(false);
        setMargin(false);
        setSizeFull();

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
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout1 = new VerticalLayout();
        VerticalLayout verticalLayout2 = new VerticalLayout();
        TextField textField = new TextField();
        Grid<User> userGrid = new Grid<>();

        horizontalLayout.setSizeFull();
        verticalLayout1.setHeightFull();
        verticalLayout1.setWidth(25, Unit.PERCENTAGE);
        verticalLayout2.setSizeFull();
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.setWidthFull();
        textField.setPlaceholder("Search");
//        userGrid.setSizeFull();

        List<User> userList = homePagePresenter.getAllUser();
        userGrid.setItems(userList);
        Grid.Column<User> userColumn = userGrid.addColumn(user -> user.getFirstName() + " " + user.getLastName()).setHeader("Contacts");

        textField.addValueChangeListener(event -> {
            ListDataProvider<User> dataProvider = (ListDataProvider) userGrid.getDataProvider();
            dataProvider.addFilter(e -> (e.getFirstName().toLowerCase() + " " + e.getLastName().toLowerCase()).contains(textField.getValue().toLowerCase()));
        });

        userGrid.addSelectionListener(event -> {
           if(event.getAllSelectedItems().size()>0){
               onGridItemSelection(verticalLayout2, event.getFirstSelectedItem().orElse(null));
           }else {
               verticalLayout2.removeAll();
           }
        });

        List<Messages> allMessages = homePagePresenter.getAllMessages();
        List<Message> messageList = new ArrayList<>();

//        messageList.add(new Message("Hi", "SK", "Sharad Kodag", true));
//        messageList.add(new Message("Hi", "VN", "Vishal Naik", false));
//        messageList.add(new Message("Hi", "SB", "Shivani Bansal", false));
//        messageList.add(new Message("Hi", "SK", "Ganesh Sangle", false));
//        messageList.add(new Message("Hi", "WS", "Waseem Shaikh", false));

        for(Messages messages : allMessages){
            if(messages.getSender().getId().equals(4) || messages.getReceiver().equals(4))
            messageList.add(new Message(messages.getMessage(), String.valueOf(messages.getReceiver().getFirstName().charAt(0)) + messages.getReceiver().getLastName().charAt(0),
                    messages.getSender().getFirstName() + " " + messages.getSender().getLastName(),messages.getSender().getId().equals(4) ));
        }

        Chat chat = new Chat();
        chat.getElement().getStyle().set("width","100%").set("height","100%");
        chat.setLoading(false);
        chat.scrollToBottom();
        chat.setMessages(messageList);
        chat.setDebouncePeriod(200);
        chat.setLazyLoadTriggerOffset(1000);
//        chat.addLazyLoadTriggerEvent(2500);

        chat.addChatNewMessageListener(event -> {
            messageList.add(new Message(event.getMessage(), "SK", "Sharad Kodag", true));
            chat.addNewMessage(new Message(event.getMessage(), "SK", "Sharad Kodag", true));
//            chat.setMessages(messageList);
            chat.scrollToBottom();
        });

        verticalLayout2.add(chat);
        verticalLayout1.add(textField, userGrid);
        horizontalLayout.add(verticalLayout1, verticalLayout2);
        return horizontalLayout;
    }

    public void onGridItemSelection(VerticalLayout verticalLayout, User user){
        Label label = new Label(user.getFirstName() + " " + user.getLastName());
        Grid<Messages> senderMessages = new Grid<>();
        Grid<Messages> receiverMessages = new Grid<>();


        label.setWidthFull();
        label.getStyle().set("background-color","green");

    }
}