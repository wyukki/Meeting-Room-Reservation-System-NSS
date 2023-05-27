package fel.cvut.cz.room_management_system.entity;

import fel.cvut.cz.room_management_system.enums.RoomFeatureEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "feature", nullable = false)
    private RoomFeatureEnum feature;
    @OneToMany(mappedBy = "feature")
    private List<RoomFeature> roomFeatures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomFeatureEnum getFeature() {
        return feature;
    }

    public void setFeature(RoomFeatureEnum feature) {
        this.feature = feature;
    }

    public List<RoomFeature> getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(List<RoomFeature> roomFeatures) {
        this.roomFeatures = roomFeatures;
    }
}
