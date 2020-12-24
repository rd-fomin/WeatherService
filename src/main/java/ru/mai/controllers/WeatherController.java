package ru.mai.controllers;

import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.mai.model.Weather;
import ru.mai.services.WeatherService;
import ru.mai.utils.WeatherUtils;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
@PropertySource({"classpath:WEB-INF/application.properties"})
public class WeatherController {
    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Value("${url}")
    private String host;
    private final String key = "1aa21815fa31432ea6f134434200312";
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping({"/"})
    public String getIndexPage(Model model) {
        model.addAttribute("weathers", WeatherUtils.WEATHERS);
        model.addAttribute("city", WeatherUtils.CITY);
        model.addAttribute("dates", WeatherUtils.DATES);
        model.addAttribute("ip", WeatherUtils.IP);
        return "index";
    }

    @GetMapping({"/get"})
    public RedirectView getWeather(@RequestParam(name = "city") String city, @RequestParam(name = "dt", required = false) Long dt) {
        String stringURL;
        String date;
        var calendar = Calendar.getInstance();
        WeatherUtils.WEATHERS = new ArrayList<>();
        if (dt != null) {
            calendar.setTimeInMillis(dt);
            date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            stringURL = host + "?q=" + city + "&key=" + key + "&format=json" + "&date=" + date;
            if (weatherService.existsByDate(date)) {
                WeatherUtils.WEATHERS.add(weatherService.findDistinctFirstByDate(date));
            } else {
                updateContent(stringURL, city);
            }
        } else {
            date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            stringURL = host + "?q=" + city + "&key=" + key + "&format=json";var startDate = LocalDate.fromDateFields(Calendar.getInstance().getTime());
            for (int i = 0; i < 14; i++) {
                var d = startDate.withFieldAdded(DurationFieldType.days(), i);
                if (weatherService.existsByDate(d.toString("yyyy-MM-dd"))) {
                    WeatherUtils.WEATHERS.add(weatherService.findDistinctFirstByDate(date));
                } else {
                    updateContent(stringURL, city);
                }
            }
        }
        logger.info("City: " + city + ", Date: " + date);
        return new RedirectView("/forecast/");
    }

    public void updateContent(String stringURL, String city) {
        try {
            var url = new URL(stringURL);
            var connection = url.openConnection();
            String content = new String(connection.getInputStream().readAllBytes());
            var jsonContent = new JSONObject(content).getJSONObject("data").getJSONArray("weather");
            WeatherUtils.CITY = city;
            WeatherUtils.WEATHERS = new ArrayList<>();
            for (int i = 0; i < jsonContent.length(); i++) {
                JSONObject jsonObject = jsonContent.getJSONObject(i);
                Weather weather = new Weather()
                        .setDate(jsonObject.getString("date"))
                        .setMinTempC(jsonObject.getString("mintempC"))
                        .setAvgTempC(jsonObject.getString("avgtempC"))
                        .setMaxTempC(jsonObject.getString("maxtempC"))
                        .setSunHour(jsonObject.getString("sunHour"));
                WeatherUtils.WEATHERS.add(weather);
                weatherService.save(weather);
                logger.info(jsonObject.toString());
            }
        } catch(MalformedURLException e){
            logger.warn("Wrong url: " + e.getMessage());
        } catch(IOException e){
            logger.warn("Something wrong with getting content: " + e.getMessage());
        }
    }

}
