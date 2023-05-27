package fel.cvut.cz.room_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity
@Table(name = "room_slot")
public class RoomSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "scheduled_from", nullable = false)
    private ZonedDateTime scheduled_from;
    @Column(name = "scheduled_to", nullable = false)
    private ZonedDateTime scheduled_to;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getScheduled_from() {
        return scheduled_from;
    }

    public void setScheduled_from(ZonedDateTime scheduled_from) {
        this.scheduled_from = scheduled_from;
    }

    public ZonedDateTime getScheduled_to() {
        return scheduled_to;
    }

    public void setScheduled_to(ZonedDateTime scheduled_to) {
        this.scheduled_to = scheduled_to;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
