package com.example.blink.whether;

/**
 * Created by blink on 4/11/2018.
 */

import android.graphics.drawable.Drawable;

/**
 * public class RowItem
 *  represent a rowItem puts together button, image and textview into one container.
 */
public class RowItem {
    private String cityName;
    private String cityTemperature;
    private String lowTemperature;
    private String highTemperature;
    private String chanceOfPrecipitation;
    private String windSpeed;
    private String humidity;
    private Drawable drawable;

    public static final String CITY_NAME = "CITY_NAME";
    public static final String TEMP = "TEMPERATURE";
    public static final String DRAWABLE = "DRAWABLE";
    public static final String LOW_TEMP = "LOW_TEMPERATURE";

    public static final String HIGH_TEMP = "HIGH_TEMPERATURE";
    public static final String PRECIPITATION = "PRECIPITATION";
    public static final String WIND_SPEED = "WIND_SPEED";
    public static final String HUMIDITY = "HUMIDITY";
    //TODO add the details in here

//    • Current temperature
//    • Hi and Low temperature for the day
//    • Chance of precipitation
//    • Any other information you find relevant. (Optional)
    public RowItem(Drawable drawable, String cityName, String cityTemperature ) {
        this.drawable = drawable;
        this.cityName = cityName;
        this.cityTemperature = cityTemperature;
    }
    public String getCityName() {
        return cityName;
    }

    public String getCityTemperature() {
        return cityTemperature;
    }

    @Override
    public String toString() {
        return cityName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public void setChanceOfPrecipitation(String chanceOfPrecipitation) {
        this.chanceOfPrecipitation = chanceOfPrecipitation;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public String getChanceOfPrecipitation() {
        return chanceOfPrecipitation;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }
}