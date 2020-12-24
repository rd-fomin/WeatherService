package ru.mai.services;

import org.springframework.stereotype.Service;
import ru.mai.model.Weather;
import ru.mai.repository.WeatherRepository;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public List<Weather> findByDate(String date) {
        return weatherRepository.findByDate(date);
    }

    @Override
    public boolean existsByDate(String date) {
        return weatherRepository.existsByDate(date);
    }

    @Override
    public Weather findDistinctFirstByDate(String date) {
        return weatherRepository.findDistinctFirstByDate(date);
    }

    @Override
    public Weather save(Weather weather) {
        return weatherRepository.save(weather);
    }

}
