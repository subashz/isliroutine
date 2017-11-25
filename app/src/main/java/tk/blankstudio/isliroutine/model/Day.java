package tk.blankstudio.isliroutine.model;

import java.util.Calendar;

/**
 * Created by deadsec on 10/31/17.
 */

public class Day {
    public static final String SUNDAY = "SUN";
    public static final String MONDAY = "MON";
    public static final String TUESDAY = "TUE";
    public static final String WEDNESDAY = "WED";
    public static final String THURSDAY = "THU";
    public static final String FRIDAY = "FRI";
    public static final String Days[]={"SUN","MON","TUE","WED","THU","FRI"};

    public static String getDay(int i) {
        return Days[i];
    }
}
