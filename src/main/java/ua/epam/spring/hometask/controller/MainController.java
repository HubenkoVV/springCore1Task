package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.view.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
public class MainController {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    @Qualifier("view")
    private View view;
    private Scanner scanner;
    private User user;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("auditoriumService")
    private AuditoriumService auditoriumService;
    @Autowired
    @Qualifier("bookingService")
    private BookingService bookingService;
    @Autowired
    @Qualifier("eventService")
    private EventService eventService;
    @Autowired
    @Qualifier("seanceService")
    private SeanceService seanceService;

    public MainController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        boolean flag = true;
        do {
            view.print(View.MENU);
            view.print(View.EXIT);
            switch (scanner.nextInt()) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    registration();
                    break;
                case 2:
                    showEvents();
                    break;
            }
        }
        while (flag);
    }

    private void menuReg() {
        boolean flag = true;
        do {
            view.print(View.MENU_REG);
            view.print(View.EXIT);
            switch (scanner.nextInt()) {
                case 0:
                    flag = false;
                    break;
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
        while (flag);
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
        menuReg();
    }

    private void showEvents() {
        List<Event> events = (List<Event>) eventService.getAll();
        view.print(View.EVENTS);
        view.printList(events);
        view.print(View.CHOOSE_EV);
        Event event = events.get(scanner.nextInt() - 1);

        view.print(View.DATES);
        view.printList(seanceService.getDatesForEvent(event.getIdevent()));
        view.print(View.CHOOSE_DATE);
        int indexDate = scanner.nextInt();
        Iterator<LocalDateTime> iterator = seanceService.getDatesForEvent(event.getIdevent()).iterator();
        LocalDateTime dateTime = null;
        while (indexDate != 0) {
            dateTime = iterator.next();
            indexDate--;
        }

        buyTickets(event, dateTime);
    }

    private void showTickets() {
        if (userService.getTickets(user).isEmpty()) {
            view.print(View.NO_TICKETS);
        } else {
            view.print(View.TICKETS);
            view.printList(userService.getTickets(user));
        }
    }

    private void buyTickets(Event event, LocalDateTime dateTime) {
        Set<Long> seats = chooseSeats(event, dateTime);
        view.print(View.PRICE);
        Auditorium auditorium = auditoriumService.getById(
                seanceService.getAuditorimForEventByDate(event.getIdevent(), dateTime));
        view.printMessages(String.valueOf(bookingService
                .getTicketsPrice(event, dateTime, user, seats,
                        auditoriumService.getSeatsVIP(auditorium))));
        Set<Ticket> tickets = new HashSet<>();
        seats.forEach(seat -> tickets.add((Ticket) appContext.getBean("ticket", user, event, dateTime, seat)));
        bookingService.bookTickets(tickets);
    }

    private Set<Long> chooseSeats(Event event, LocalDateTime dateTime) {
        view.print(View.AUDITORIUM, View.CHOOSE_SEAT);
        Auditorium auditorium = auditoriumService.getById(
                seanceService.getAuditorimForEventByDate(event.getIdevent(), dateTime));
        view.printMessages(String.valueOf(auditorium.getNumberOfSeats()));
        view.print(View.VIP);
        view.printList(auditoriumService.getSeatsVIP(auditorium));
        view.print(View.EXIT);

        Set<Long> seats = new HashSet<>();

        int choise;
        while (true) {
            choise = scanner.nextInt();
            if (choise == 0)
                break;
            seats.add((long) choise);
        }

        return seats;

    }
}
