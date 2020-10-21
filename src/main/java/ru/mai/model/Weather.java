package ru.mai.model;

public class Weather {

    private String date;
    private String minTempC;
    private String avgTempC;
    private String maxTempC;
    private String sunHour;

    public Weather() {  }

    public String getDate() {
        return date;
    }

    public Weather setDate(String date) {
        this.date = date;
        return this;
    }

    public String getMinTempC() {
        return minTempC + " °C";
    }

    public Weather setMinTempC(String minTempC) {
        this.minTempC = minTempC;
        return this;
    }

    public String getAvgTempC() {
        return avgTempC + " °C";
    }

    public Weather setAvgTempC(String avgTempC) {
        this.avgTempC = avgTempC;
        return this;
    }

    public String getMaxTempC() {
        return maxTempC + " °C";
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
