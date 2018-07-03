package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.view.View;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class MainController {

    @Autowired
    private ApplicationContext appContext;
    private View view;
    private Scanner scanner;
    private User user;
    private UserService userService;

    public MainController(View view, Scanner scanner, UserService userService) {
        this.view = view;
        this.scanner = scanner;
        this.userService = userService;
    }

    public void menu() {
        view.print(View.MENU);
        switch (scanner.nextInt()) {
            case 1:
                registration();
                break;
            case 2:
                showEvents();
                break;
        }
    }

    public void menuReg() {
        view.print(View.MENU_REG);
        switch (scanner.nextInt()) {
            case 1:
                user = null;
                menu();
                break;
            case 2:
                showEvents();
                break;
            case 3:
                showTickets();
                break;
        }
    }

    private void registration() {
        User userCreate = (User) appContext.getBean("user");
        view.print(View.NAME);
        userCreate.setFirstName(scanner.next());
        view.print(View.SURNAME);
        userCreate.setLastName(scanner.next());
        view.print(View.MAIL);
        userCreate.setEmail(scanner.next());
        view.print(View.BIRTH);
        userCreate.setBirthday(LocalDate.parse(scanner.next()));
        this.user = userService.save(userCreate);
    }

    private void showEvents() {
        view.print(View.EVENTS);
    }

    private void showTickets() {
        if (user.getTickets().size() == 0) {
            view.print(View.NO_TICKETS);
        } else {
            view.print(View.TICKETS);
            view.printList(user.getTickets());
        }
    }
}
