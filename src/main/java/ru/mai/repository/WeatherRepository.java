package ru.mai.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import ru.mai.model.Weather;

import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, String> {

    List<Weather> findByDate(String date);

    boolean existsByDate(String date);

}
