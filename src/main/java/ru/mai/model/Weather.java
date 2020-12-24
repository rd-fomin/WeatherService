package ru.mai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("weather")
public class Weather {

    @Id
    private String id;
    @Indexed
    private String date;
    private String minTempC;
    private String avgTempC;
    private String maxTempC;
    private String sunHour;

    public Weather() {  }

    public String getId() {
        return id;
    }

    public Weather setId(String id) {
        this.id = id;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Weather setDate(String date) {
        this.date = date;
        return this;
    }

    public String getMinTempC() {
        return minTempC;
    }

    public Weather setMinTempC(String minTempC) {
        this.minTempC = minTempC;
        return this;
    }

    public String getAvgTempC() {
        return avgTempC;
    }

    public Weather setAvgTempC(String avgTempC) {
        this.avgTempC = avgTempC;
        return this;
    }

    public String getMaxTempC() {
        return maxTempC;
    }

    public Weather setMaxTempC(String maxTempC) {
        this.maxTempC = maxTempC;
        return this;
    }

    public String getSunHour() {
        return sunHour;
    }

    public Weather setSunHour(String sunHour) {
        this.sunHour = sunHour;
        return this;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", minTempC='" + minTempC + '\'' +
                ", avgTempC='" + avgTempC + '\'' +
                ", maxTempC='" + maxTempC + '\'' +
                ", sunHour='" + sunHour + '\'' +
                '}';
    }
}
