package ru.mai.controllers;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.mai.model.Weather;
import ru.mai.utils.WeatherUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
@PropertySource({"classpath:WEB-INF/application.properties"})
public class WeatherController {
    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final String host;

    public WeatherController(@Value("${url}") String host) {
        this.host = host;
    }

    @GetMapping({"/"})
    public String getIndexPage(Model model) {
        model.addAttribute("weathers", WeatherUtils.WEATHERS);
        model.addAttribute("city", WeatherUtils.CITY);
        model.addAttribute("dates", WeatherUtils.DATES);
        return "index";
    }

    @GetMapping({"/get"})
    public RedirectView getWeather(@RequestParam(name = "city") String city, @RequestParam(name = "dt", required = false) Long dt) {
        String stringURL;
        if (dt != null) {
            var calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dt);
            DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            stringURL = host + "?q=" + city + "&key=df1f116fe46d494e930104120200410&format=json&date=" + dataFormat.format(calendar.getTime());
        } else {
            stringURL = host + "?q=" + city + "&key=df1f116fe46d494e930104120200410&format=json";
        }
        try {
            var url = new URL(stringURL);
            var connection = url.openConnection();
            String content = new String(connection.getInputStream().readAllBytes());
            var jsonContent = new JSONObject(content).getJSONObject("data").getJSONArray("weather");
            WeatherUtils.CITY = city;
            logger.info((dt != null) ? "City: " + city + ", Date: " + dt : "City: " + city);
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
                logger.info(jsonObject.toString());
            }
        } catch(MalformedURLException e){
            logger.warn("Wrong url: " + e.getMessage());
        } catch(IOException e){
            logger.warn("Something wrong with getting content: " + e.getMessage());
        }
        return new RedirectView("/forecast/");
    }

}
