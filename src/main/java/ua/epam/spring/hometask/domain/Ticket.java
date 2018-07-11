package ua.epam.spring.hometask.domain;

/**
 * @author Yuriy_Tkach
 */
public class Ticket implements Comparable<Ticket> {

    private Long user;

    private Long seance;

    private long seat;

    private Long idticket;

    public Ticket() {
    }

    public Ticket(User user, Long seance, long seat) {
        if (user != null)
            this.user = user.getIduser();
        this.seance = seance;
        this.seat = seat;
    }

    public Long getIdticket() {
        return idticket;
    }

    public void setIdticket(Long idticket) {
        this.idticket = idticket;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public Long getSeance() {
        return seance;
    }

    public void setSeance(Long seance) {
        this.seance = seance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (seat != ticket.seat) return false;
        if (user != null ? !user.equals(ticket.user) : ticket.user != null) return false;
        return seance.equals(ticket.seance);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + seance.hashCode();
        result = 31 * result + (int) (seat ^ (seat >>> 32));
        return result;
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = seance.compareTo(other.getSeance());
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }


}
