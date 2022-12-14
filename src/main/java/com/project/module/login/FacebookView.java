package com.project.module.login;

import com.project.entity.User;
import com.project.mvputil.BaseView;
import com.project.serviceimpl.JavaMail;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@UIScope
@SpringComponent
public class FacebookView extends BaseView<FacebookPresenter> {

    @Autowired
    FacebookPresenter presenter;
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout verticalLayout1  = new VerticalLayout();

    @Override
    protected void init() {
        setSizeFull();
        setPadding(false);
        setMargin(false);
        horizontalLayout.setSizeFull();
        horizontalLayout.getStyle().set("background-color", "azure");
        verticalLayout1.setAlignItems(FlexComponent.Alignment.START);
        verticalLayout1.getStyle().set("margin-right","25%").set("margin-top", "10%");
        verticalLayout.getStyle().set("margin-left","25%").set("margin-top", "10%");

        addComponent();
        horizontalLayout.add(verticalLayout,verticalLayout1);
        add(horizontalLayout);
    }

    public void addComponent(){
        Label h1 = new Label("facebook");
        FormLayout formLayout = new FormLayout();
        TextField textField = new TextField();
        PasswordField textField1 = new PasswordField();
        Binder<User> binder = new Binder<>();
        Button button = new Button("Log in");
        RouterLink routerLink = new RouterLink();
        Hr hr = new Hr();
        Button button1 = new Button("Create New Account");

        h1.getStyle().set("color","blue").set("font-size", "400%").set("font-weight", "bold");
        Label label = new Label("Facebook helps you connect and share with the people in life.");
        label.getStyle().set("font-size", "x-large");
        formLayout.getStyle().set("background-color", "white").set("padding","5%");
        button.getStyle().set("color","white").set("background-color", "blue");
        routerLink.getStyle().set("color", "blue");

        textField.setPlaceholder("Email");
        textField1.setPlaceholder("Password");
        routerLink.setText("Forgot Password");

        textField.addValueChangeListener(event -> {
            new EmailValidator("You'll use this when you log in and if"  +
                    " you ever need to reset your password");
        });
        binder.forField(textField).withValidator(new EmailValidator("Enter valid email"))
                .bind(User::getEmail, User::setEmail);
        binder.forField(textField1)
                .withValidator(e -> textField1.getValue().length()>=6, "Enter valid Password")
                .bind(User::getPassword,User::setPassword);
        button.addClickListener(event -> {
            if(binder.isValid()) {
                User user = presenter.getUserList().stream()
                        .filter(e -> e.getEmail().equals(textField.getValue()) && e.getPassword().equals(textField1.getValue())).findFirst().orElse(null);
                if (user != null) {
                    System.out.println(user.toString());
                    VaadinSession.getCurrent().setAttribute("user", user);
                    button.getUI().ifPresent(e -> {
                        e.navigate("homepage");
                    });
                } else {
                    Notification invalidCredential = Notification.show("Invalid Credential", 2000, Notification.Position.TOP_CENTER);
                    invalidCredential.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }else {
                binder.validate();
            }
        });
        button1.getStyle().set("background-color", "limegreen").set("color","white");
        button1.addClickListener(event -> {
            addSignUp();
        });

        formLayout.add(textField, textField1, button, routerLink, hr, button1);
        verticalLayout.add(h1,label);
        verticalLayout1.add(formLayout);

    }

    public void addSignUp(){
        Dialog dialog = new Dialog();
        Label text1 = new Label("It's quick and easy");
        Button close = new Button();
        VerticalLayout verticalLayout2= new VerticalLayout();
        VerticalLayout verticalLayout3 = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        FormLayout formLayout = new FormLayout();
        Hr hr = new Hr();
        TextField textField = new TextField();
        TextField textField1 = new TextField();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        DatePicker datePicker = new DatePicker("Date of Birth");
        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        Label text2 = new Label("People who use our service may have uploaded your contact information to Facebook. Learn more.\n" +
                "\n");
        Label label = new Label("By clicking Sign Up, you agree to our Terms, Privacy Policy and Cookies Policy. You may receive SMS notifications from us and can opt out at any time.");
        Binder<User> binder = new Binder<>();
        User user = new User();
        Button signUp = new Button("Sign Up");
        Label text = new Label("Sign Up");

        text.getStyle().set("font-size", "200%").set("font-weight", "bold");
        close.setIcon(VaadinIcon.CLOSE.create());
        verticalLayout3.setAlignItems(FlexComponent.Alignment.END);
        text2.getStyle().set("font-size", "70%");
        label.getStyle().set("font-size", "70%");
        signUp.getStyle().set("background-color", "limegreen").set("color","white");

        textField.setPlaceholder("First name");
        passwordField.setPlaceholder("Password");
        textField1.setPlaceholder("Last name");
        emailField.setPlaceholder("Email id");
        gender.setLabel("Gender");
        gender.setItems("Male", "Female", "Custom");

        close.addClickListener(event -> dialog.close());
        emailField.addValueChangeListener(event -> {
            new EmailValidator("You'll use this when you log in and if"  +
                    " you ever need to reset your password");
        });

        signUp.addClickListener(event -> {
            binder.forField(textField).withValidator(u -> u.length()>0, "What's your name")
                    .bind(User::getFirstName,User::setFirstName);
            binder.forField(textField1).withValidator(u -> u.length()>0, "What's your name")
                    .bind(User::getLastName,User::setLastName);
            binder.forField(emailField).withValidator(new EmailValidator("You'll use this when you log in and if" +
                            " you ever need to reset your password"))
                    .bind(User::getEmail,User::setEmail);
            binder.forField(passwordField).withValidator(u -> u.length()>=6, "Enter Combination at least six numbers, letters" +
                            " and punctuation marks (such as ! and &).")
                    .bind(User::getPassword,User::setPassword);
            binder.forField(datePicker)
                    .withValidator( e -> !datePicker.isEmpty(), "Enter date of birth").bind(User::getDob, User::setDob);
            binder.forField(gender).withValidator(e -> !gender.isEmpty(), "Select gender").bind(User::getGender, User::setGender);
            try {
                binder.writeBean(user);
                VaadinSession.getCurrent().setAttribute("user", user);
            } catch (ValidationException e) {
                System.out.println();
            }

            if(binder.isValid()){
                new JavaMail().sendMail(emailField.getValue());
                Dialog dialog1 = new Dialog();
                Button button = new Button("Submit");
                TextField textField2 = new TextField("Enter OTP");

                dialog1.open();

                button.getStyle().set("background-color", "green").set("color", "white");

                dialog1.add(textField2,button);

                int otp = (int) VaadinSession.getCurrent().getAttribute("otp");

                button.addClickListener(event1 -> {
                    if(textField2.getValue().equals(String.valueOf(otp))){
                        presenter.addUser(user);
                        Notification notification = Notification.show("Account Created Successfully.", 2000, Notification.Position.TOP_CENTER);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        dialog1.close();
                        dialog.close();

                    }else {
                        Notification wrongOtp = Notification.show("wrong OTP", 2000, Notification.Position.TOP_CENTER);
                        wrongOtp.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                });
            }
        });

        verticalLayout2.add(text,text1);
        verticalLayout3.add(close);
        horizontalLayout.add(verticalLayout2,verticalLayout3);
        horizontalLayout1.add(textField,textField1);
        formLayout.add(horizontalLayout1, emailField, passwordField, datePicker, gender, text2, label, signUp);
        dialog.add(horizontalLayout, hr, formLayout);
        dialog.setWidth(30, Unit.PERCENTAGE);
        dialog.open();
    }
}