package ua.epam.spring.hometask.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.epam.spring.hometask.aspect.CounterAspect;
import ua.epam.spring.hometask.aspect.DiscountAspect;
import ua.epam.spring.hometask.aspect.LuckyWinnerAspect;
import ua.epam.spring.hometask.controller.MainController;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.service.impl.*;
import ua.epam.spring.hometask.service.impl.strategy.BirthdayStrategy;
import ua.epam.spring.hometask.service.impl.strategy.DiscountStrategy;
import ua.epam.spring.hometask.service.impl.strategy.TenthStrategy;
import ua.epam.spring.hometask.view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Vladyslava_Hubenko on 7/3/2018.
 */
@Configuration
@PropertySource("classpath:auditorium.properties")
@EnableAspectJAutoProxy
public class ConfigClass {

    @Autowired
    private Environment env;

    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }

    @Bean
    @Scope("prototype")
    public Ticket ticket(User user, Event event, LocalDateTime dateTime, long seat) {
        return new Ticket(user, event, dateTime, seat);
    }

    @Bean
    @Scope("prototype")
    public Auditorium auditorium(int i) {
        Auditorium auditorium = new Auditorium();
        Set<Long> seatsVIP = new HashSet<>();
        Arrays.asList(env.getProperty("auditorium" + i + ".VIP").split(","))
                .forEach(s -> seatsVIP.add(Long.valueOf(s)));
        auditorium.setVipSeats(seatsVIP);
        auditorium.setNumberOfSeats(Long.valueOf(env.getProperty("auditorium" + i + ".seats")));
        auditorium.setName(env.getProperty("auditorium" + i + ".name"));
        return auditorium;
    }

    @Bean
    public List<Event> events() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Event event = event();
            event.setName("SMTH");
            event.setBasePrice(73);
            event.setAirDates(dates());
            event.setAuditoriums(auditoriumsForDate(dates()));
            event.setRating(EventRating.LOW);
            events.add(event);
        }
        return events;
    }

    @Bean
    public NavigableSet<LocalDateTime> dates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        NavigableSet<LocalDateTime> dateTimes = new TreeSet<>();
        dateTimes.add(LocalDateTime.parse("2018-08-13 12:30", formatter));
        dateTimes.add(LocalDateTime.parse("2018-08-14 12:30", formatter));
        dateTimes.add(LocalDateTime.parse("2018-08-15 12:30", formatter));
        return dateTimes;
    }

    @Bean
    @DependsOn(value = "auditoriumDAO")
    public NavigableMap<LocalDateTime, Auditorium> auditoriumsForDate(NavigableSet<LocalDateTime> dateTimes) {
        NavigableMap<LocalDateTime, Auditorium> auditoriumsForDate = new TreeMap<>();
        List<Auditorium> auditoriums = auditoriumDAO().getAll();
        Iterator<LocalDateTime> localDateTimeIterator = dateTimes.iterator();
        int i = 0;
        while (localDateTimeIterator.hasNext()) {
            auditoriumsForDate.put(localDateTimeIterator.next(), auditoriums.get(i));
            i++;
        }
        return auditoriumsForDate;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    public EventDAO eventDAO() {
        return new EventDAO();
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl(eventDAO());
    }

    @Bean
    public AuditoriumDAO auditoriumDAO() {
        List<Auditorium> auditoriums = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            auditoriums.add(auditorium(i));
        }
        return new AuditoriumDAO(auditoriums);
    }

    @Bean
    public AuditoriumService auditoriumService() {
        return new AuditoriumServiceImpl(auditoriumDAO());
    }

    @Bean
    public BookingService bookingService() {
        return new BookingServiceImpl(discountService());
    }

    @Bean
    public DiscountService discountService() {
        List<DiscountStrategy> strategyList = new ArrayList<>();
        strategyList.add(birth());
        strategyList.add(tenth());
        return new DiscountServiceImpl(strategyList);
    }

    @Bean
    public DiscountStrategy birth() {
        return new BirthdayStrategy();
    }

    @Bean
    public DiscountStrategy tenth() {
        return new TenthStrategy();
    }

    @Bean
    public MainController controller() {
        return new MainController(view(), new Scanner(System.in), userService());
    }

    @Bean
    public View view() {
        return new View();
    }

    @Bean
    public CounterAspect aspect() {
        return new CounterAspect();
    }

    @Bean
    public DiscountAspect discountAspect() {
        return new DiscountAspect();
    }

    @Bean
    public LuckyWinnerAspect winnerAspect() {
        return new LuckyWinnerAspect();
    }
}
