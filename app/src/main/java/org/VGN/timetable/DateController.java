package org.VGN.timetable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateController {

    private static Date currentDate;

    public static String getDate () {
        return setDatePattern("yyyy/MM/dd HH:mm:ss").format(currentDate);
    }

    public static String getDayOfWeek () {
        return setDatePattern("EEEE").format(currentDate);
    }

    public static String getDay () {
        return setDatePattern("dd.MM").format(currentDate);
    }

    private static DateFormat setDatePattern (String pattern) {
        currentDate = new Date();
        return new SimpleDateFormat(pattern);
    }
}
