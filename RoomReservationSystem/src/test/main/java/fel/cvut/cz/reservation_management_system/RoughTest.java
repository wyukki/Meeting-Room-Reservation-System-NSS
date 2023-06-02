package main.java.fel.cvut.cz.reservation_management_system;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class RoughTest {

    @Test
    public void dayOfWeekTest(){
        ZonedDateTime date = ZonedDateTime.now();

        System.out.println(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));

        System.out.println(date.plusDays(0));

        System.out.println(date.plusWeeks(1));
    }

}
