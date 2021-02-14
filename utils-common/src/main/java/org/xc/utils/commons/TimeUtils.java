package org.xc.utils.commons;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeUtils {

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long between(ChronoUnit unit, LocalDateTime t1, LocalDateTime t2) {
        return unit.between(t1, t2);
    }

    public static List<LocalDateTime> getAllBetween(LocalDateTime t1, LocalDateTime t2, long step, ChronoUnit unit) {
        List<LocalDateTime> result = new ArrayList();
        while (!t1.isAfter(t2)) {
            result.add(t1);
            t1 = t1.plus(step, unit);
        }
        return result;
    }

    public static List<String> getAllBetween(LocalDateTime t1, LocalDateTime t2, long step, ChronoUnit unit, String pattern) {
        List<String> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        while (!t1.isAfter(t2)) {
            result.add(formatter.format(t1));
            t1 = t1.plus(step, unit);
        }
        return result;
    }

    static class PATTERN {
        public static final String DEFAULT = "yyyy-MM-dd HH:mm:ss";
        public static final String DEFAULT_DATE = "yyyy-MM-dd";
        public static final String MONTH_DAY = "yyyy-MM";
        public static final String UTC = "yyyy-MM-dd'T'HH:mm:ss";
    }


}
