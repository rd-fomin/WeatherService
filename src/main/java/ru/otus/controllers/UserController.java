package ru.otus.controllers;

import org.json.JSONArray;
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
import ru.otus.model.Weather;
import ru.otus.utils.WeatherUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Controller
@PropertySource({"classpath:WEB-INF/application.properties"})
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final String host;

    public UserController(@Value("${url}") String host) {
        this.host = host;
    }

    @GetMapping({"/"})
    public String getIndexPage(Model model) {
        model.addAttribute("weathers", WeatherUtils.WEATHERS);
        model.addAttribute("city", WeatherUtils.city);
        return "index";
    }

    @GetMapping({"/get"})
    public RedirectView saveWeather(@RequestParam(name = "city") String city) {
        try {
            URL url = new URL(host + "?q=" + city + "&key=df1f116fe46d494e930104120200410&format=json");
            URLConnection connection = url.openConnection();
            String content = new String(connection.getInputStream().readAllBytes());
            JSONArray jsonContent = new JSONObject(content).getJSONObject("data").getJSONArray("weather");
            WeatherUtils.city = city;
            logger.info("City: " + city);
            for (int i = 0; i < jsonContent.length(); i++) {
                JSONObject jsonObject = jsonContent.getJSONObject(i);
                Weather weather = new Weather();
                weather.setDate(jsonObject.getString("date"));
                weather.setMintempC(jsonObject.getString("mintempC"));
                weather.setAvgtempC(jsonObject.getString("avgtempC"));
                weather.setMaxtempC(jsonObject.getString("maxtempC"));
                weather.setSunHour(jsonObject.getString("sunHour"));
                WeatherUtils.WEATHERS.add(weather);
                logger.info(jsonObject.toString());
            }
        } catch (MalformedURLException e) {
            logger.warn("Wrong url: " + e.getMessage());
        } catch (IOException e) {
            logger.warn("Something wrong with getting content: " + e.getMessage());
        }
        return new RedirectView("/weather/");
    }

}
