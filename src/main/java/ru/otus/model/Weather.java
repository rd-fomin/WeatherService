package ru.otus.model;

public class Weather {

    private String date;
    private String mintempC;
    private String avgtempC;
    private String maxtempC;
    private String sunHour;

    public Weather() {  }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getAvgtempC() {
        return avgtempC;
    }

    public void setAvgtempC(String avgtempC) {
        this.avgtempC = avgtempC;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getSunHour() {
        return sunHour;
    }

    public void setSunHour(String sunHour) {
        this.sunHour = sunHour;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", mintempC='" + mintempC + '\'' +
                ", avgtempC='" + avgtempC + '\'' +
                ", maxtempC='" + maxtempC + '\'' +
                ", sunHour='" + sunHour + '\'' +
                '}';
    }
}
