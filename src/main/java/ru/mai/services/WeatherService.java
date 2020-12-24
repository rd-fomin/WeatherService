package ru.mai.services;

import ru.mai.model.Weather;

import java.util.List;

public interface WeatherService {

    List<Weather> findByDate(String date);

    boolean existsByDate(String date);

    Weather findDistinctFirstByDate(String date);

    Weather save(Weather weather);

}
