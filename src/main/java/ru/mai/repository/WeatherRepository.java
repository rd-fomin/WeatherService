package ru.mai.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import ru.mai.model.Weather;

import java.util.List;

@Repository
public interface WeatherRepository extends KeyValueRepository<Weather, String> {

    List<Weather> findByDate(String date);

    boolean existsByDate(String date);

    Weather findDistinctFirstByDate(String date);

}
