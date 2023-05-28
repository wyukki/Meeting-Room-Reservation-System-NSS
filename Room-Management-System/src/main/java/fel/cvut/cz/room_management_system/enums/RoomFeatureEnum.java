package fel.cvut.cz.room_management_system.enums;

import fel.cvut.cz.room_management_system.exceptions.NotFoundException;

public enum RoomFeatureEnum {
    AC("AC"),
    PROJECTOR("PROJECTOR"),
    WHITEBOARD("WHITEBOARD"),
    PRINTER("PRINTER"),
    SOUND_SYSTEM("SOUND_SYSTEM"),
    WIFI("WIFI"),
    PHONE("PHONE"),
    ACCESSIBILITY("ACCESSIBILITY"),
    REFRESHMENT("REFRESHMENT");
    private final String value;

    RoomFeatureEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomFeatureEnum fromValue(String value) throws NotFoundException {
        for (RoomFeatureEnum feature : RoomFeatureEnum.values()) {
            if (feature.value.equals(value)) {
                return feature;
            }
        }
        throw new NotFoundException("Value " + value + " does not supported!");
    }
}
