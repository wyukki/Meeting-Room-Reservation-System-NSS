package fel.cvut.cz.room_management_system.dto;

import fel.cvut.cz.room_management_system.entity.RoomSlot;

import java.util.List;
import java.util.Set;

public record RoomDTO(Long id, Long buildingId, String name, String description, String imageURL, Boolean occupiedNow,
                      Long capacity, List<RoomSlot> reservedSlots, Set<RoomFeatureDTO> roomFeatures,
                      String roomLayout, String noiseLevel) {
}
