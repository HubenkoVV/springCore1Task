package ua.epam.spring.hometask.view;

import java.util.Collection;
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
    public static final String CHOOSE_EV = "choose.event";
    public static final String CHOOSE_DATE = "choose.date";
    public static final String DATES = "dates";
    public static final String AUDITORIUM = "auditorium";
    public static final String SEATS = "seats";
    public static final String CHOOSE_SEAT = "choose.seat";
    public static final String THATS_ALL = "thats.all";
    public static final String VIP = "VIP";
    public static final String EXIT = "exit";
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public void print(String... messages) {
        for (String mes : messages) {
            System.out.println(resourceBundle.getString(mes));
        }
    }

    public void printMessages(String... messages) {
        for (String mes : messages) {
            System.out.println(mes);
        }
    }

    public void printList(Collection<?> objects) {
        objects.forEach(o -> System.out.println(o.toString()));
    }
}
