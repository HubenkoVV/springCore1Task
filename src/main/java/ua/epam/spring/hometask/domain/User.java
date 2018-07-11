package ua.epam.spring.hometask.domain;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * @author Yuriy_Tkach
 */
public class User {

    private Long iduser;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    private NavigableSet<Ticket> tickets = new TreeSet<>();

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NavigableSet<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(NavigableSet<Ticket> tickets) {
        this.tickets.addAll(tickets);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return iduser.equals(user.iduser);
    }

    @Override
    public int hashCode() {
        return iduser.hashCode();
    }
}
