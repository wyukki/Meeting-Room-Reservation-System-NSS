package fel.cvut.cz.reservation_management_system.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roomId", nullable = false)
    private Long roomId;

    @Column(name = "dateFrom", nullable = false)
    private ZonedDateTime dateFrom;

    @Column(name = "dateTo", nullable = false)
    private ZonedDateTime dateTo;

    @ManyToOne
    @JoinColumn(name = "system_user_id", insertable = false, updatable = false)
    private User myUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public User getUser() {
        return myUser;
    }

    public void setUser(User user) {
        this.myUser = user;
    }
}
