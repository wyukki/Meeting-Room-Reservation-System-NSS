package fel.cvut.cz.room_management_system.dto;

import fel.cvut.cz.room_management_system.entity.RoomSlot;

import java.util.List;

public record RoomDTO(Long id, Long buildingId, String name, String description, String imageURL, Boolean occupiedNow,
                      Long capacity, List<RoomSlot> reservedSlots, List<RoomFeatureDTO> roomFeatures,
                      String roomLayout, String noiseLevel) {
}
