package ua.epam.spring.hometask.view;

import ua.epam.spring.hometask.domain.Ticket;

import java.util.NavigableSet;
import java.util.ResourceBundle;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class View {

    public static final String MENU = "menu";
    public static final String MENU_REG = "menu.reg";
    public static final String EVENTS = "events";
    public static final String TICKETS = "tickets";
    public static final String NO_TICKETS = "no.tickets";
    public static final String PRICE = "price";
    public static final String NAME = "enter.name";
    public static final String SURNAME = "enter.surname";
    public static final String MAIL = "enter.mail";
    public static final String BIRTH = "enter.birthday";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public void print(String... messages) {
        for (String mes : messages) {
            System.out.println(resourceBundle.getString(mes));
        }
    }

    public void printList(NavigableSet<Ticket> objects) {
        objects.forEach(o -> System.out.println(o.toString()));
    }
}
