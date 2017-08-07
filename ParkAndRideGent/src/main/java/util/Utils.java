package util;

import java.time.LocalDateTime;
import java.time.Month;
import org.joda.time.DateTime;

public class Utils {
    public static LocalDateTime dateTimeToLocalDateTime(DateTime time) {
        return LocalDateTime.of(
                                    time.getYear(),
                                    Month.of(time.getMonthOfYear()),
                                    time.getDayOfMonth(),
                                    time.getHourOfDay(),
                                    time.getMinuteOfHour(),
                                    time.getSecondOfMinute()
                                );

    }
}