package fel.cvut.cz.reservation_management_system.enums;

import fel.cvut.cz.reservation_management_system.exception.NotFoundException;

public enum RecurrentEnum {
    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH");

    private final String value;

    RecurrentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RecurrentEnum fromValue(String value) throws NotFoundException {
        for (RecurrentEnum feature : RecurrentEnum.values()) {
            if (feature.value.equals(value)) {
                return feature;
            }
        }
        throw new NotFoundException("Value " + value + " does not supported!");
    }
}
