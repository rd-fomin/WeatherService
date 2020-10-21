package ru.mai.utils;

import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import ru.mai.model.Weather;

import java.util.*;

public class WeatherUtils {
    public static String CITY = "";
    public static List<Weather> WEATHERS = new ArrayList<>();
    public static final Map<String, Long> DATES = new TreeMap<>();

    static {
        var startDate = LocalDate.fromDateFields(Calendar.getInstance().getTime());
        for (int i = 0; i < 14; i++) {
            var d = startDate.withFieldAdded(DurationFieldType.days(), i);
            DATES.put(d.toString("dd-MM-yyyy"), d.toDateTimeAtCurrentTime().getMillis());
        }
    }

    private WeatherUtils() {}

}
