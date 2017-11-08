package com.example.deadsec.isliroutine.model;

import java.util.Calendar;

/**
 * Created by deadsec on 10/31/17.
 */

public enum Day {
    SUNDAY(Calendar.SUNDAY),
    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY);

    private final int value;

    Day(int value) {
        this.value=value;
    }

    public static Day getByValue(final int value) {
        for(final Day day: Day.values()) {
            if(day.value == value) {
                return day;
            }
        }
        return null;
    }

    public final int getValue() {
        return value;
    }
}
