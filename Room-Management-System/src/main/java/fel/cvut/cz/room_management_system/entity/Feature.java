package fel.cvut.cz.room_management_system.entity;

import fel.cvut.cz.room_management_system.enums.RoomFeatureEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "feature", nullable = false)
    private RoomFeatureEnum featureName;
    @OneToMany(mappedBy = "feature")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<RoomFeature> roomFeatures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomFeatureEnum getFeatureName() {
        return featureName;
    }

    public void setFeatureName(RoomFeatureEnum feature) {
        this.featureName = feature;
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
}
