package ua.epam.spring.hometask.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.aspect.CounterAspect;
import ua.epam.spring.hometask.aspect.DiscountAspect;
import ua.epam.spring.hometask.aspect.LuckyWinnerAspect;
import ua.epam.spring.hometask.controller.MainController;
import ua.epam.spring.hometask.dao.AuditoriumDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.SeanceDAO;
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
@PropertySources({
        @PropertySource("classpath:auditorium.properties"),
        @PropertySource("classpath:db.properties")
})
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
    @DependsOn(value = "auditoriumDAO")
    public List<Auditorium> auditoriums() {
        List<Auditorium> auditoriums = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Auditorium auditorium = auditorium(i);
            auditorium.setIdauditorium(auditoriumDAO().addAuditorium(auditorium));
            auditoriums.add(auditorium);
        }
        return auditoriums;
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }

    @Bean
    public NavigableSet<LocalDateTime> dates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        NavigableSet<LocalDateTime> dateTimes = new TreeSet<>();
        dateTimes.add(LocalDateTime.parse("2018-08-13 12:30:00", formatter));
        dateTimes.add(LocalDateTime.parse("2018-08-14 12:30:00", formatter));
        dateTimes.add(LocalDateTime.parse("2018-08-15 12:30:00", formatter));
        return dateTimes;
    }

    @Bean
    @Scope("prototype")
    public Seance seance(LocalDateTime date, Long event, Long auditorium) {
        Seance seance = new Seance();
        seance.setAuditorium(auditorium);
        seance.setDateTime(date);
        seance.setEvent(event);
        return seance;
    }

    @Bean
    @Scope("prototype")
    @DependsOn(value = {"seanceDAO", "auditoriumDAO"})
    public List<Seance> seances(NavigableSet<LocalDateTime> dateTimes, Long idEvent) {
        List<Seance> seances = new ArrayList<>();
        List<Auditorium> auditoriums = auditoriums();
        Iterator<LocalDateTime> localDateTimeIterator = dateTimes.iterator();
        int i = 0;
        while (localDateTimeIterator.hasNext()) {
            Seance seance = seance(localDateTimeIterator.next(), idEvent, auditoriums.get(i).getIdauditorium());
            seances.add(seance);
            i++;
        }

        seances.forEach(seance -> seanceDAO().addSeance(seance));
        return seances;
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(template());
    }

    @Bean
    public EventDAO eventDAO() {
        EventDAO eventDAO = new EventDAO(template());
        eventDAO.deleteAll();

        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Event event = event();
            event.setName("SMTH" + ".." + i + "..");
            event.setBasePrice(73);
            event.setAirDates(dates());
            event.setRating(EventRating.LOW);
            event.setIdevent(eventDAO.addEvent(event));
            events.add(event);
        }

        for (Event event : events) {
            seances(dates(), event.getIdevent());
        }
        return eventDAO;
    }

    @Bean
    @DependsOn(value = "seanceDAO")
    public AuditoriumDAO auditoriumDAO() {
        AuditoriumDAO auditoriumDAO = new AuditoriumDAO(template());
        auditoriumDAO.deleteAll();
        return auditoriumDAO;
    }

    @Bean
    public SeanceDAO seanceDAO() {
        SeanceDAO seanceDAO = new SeanceDAO(template());
        seanceDAO.deleteAll();
        return seanceDAO;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl(eventDAO());
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
    public SeanceService seanceService() {
        return new SeanceServiceImpl(seanceDAO());
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
        return new MainController(new Scanner(System.in));
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

    @Bean
    public JdbcTemplate template() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }
}
