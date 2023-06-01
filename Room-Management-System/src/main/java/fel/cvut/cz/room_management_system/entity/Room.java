package fel.cvut.cz.room_management_system.entity;

import fel.cvut.cz.room_management_system.enums.NoiseLevel;

import fel.cvut.cz.room_management_system.enums.RoomLayoutEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "img_url")
    private String imgURL;
    @Column(name = "occupied_now", nullable = false)
    private Boolean occupiedNow;

    @Column(name = "capacity", nullable = false)
    private Long capacity;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<RoomFeature> roomFeatures;

    @Enumerated(EnumType.STRING)
    @Column(name = "noise_level")
    private NoiseLevel noiseLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_layout")
    private RoomLayoutEnum roomLayout;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomSlot> roomSlots;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "building_id", insertable = false, updatable = false)
    private Building building;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Boolean getOccupiedNow() {
        return occupiedNow;
    }

    public void setOccupiedNow(Boolean occupiedNow) {
        this.occupiedNow = occupiedNow;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Set<RoomFeature> getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(Set<RoomFeature> roomFeatures) {
        this.roomFeatures.clear();
        if (roomFeatures != null) {
            this.roomFeatures.addAll(roomFeatures);
        }
    }

    public NoiseLevel getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(NoiseLevel noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public RoomLayoutEnum getRoomLayout() {
        return roomLayout;
    }

    public void setRoomLayout(RoomLayoutEnum roomLayout) {
        this.roomLayout = roomLayout;
    }

    public List<RoomSlot> getRoomSlots() {
        return roomSlots;
    }

    public void setRoomSlots(List<RoomSlot> roomSlots) {
        this.roomSlots = roomSlots;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
