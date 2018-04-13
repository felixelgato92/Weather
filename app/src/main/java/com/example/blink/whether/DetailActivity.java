package com.example.blink.whether;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String cityName;
    private String cityTemperature;
    private String lowTemperature;
    private String highTemperature;
    private String chanceOfPrecipitation;
    private String windSpeed;
    private String humidity;
    private Bitmap bitmap;

    private TextView textView_cityName;
    private TextView textView_cityTemperature;
    private TextView textView_lowTemperature;
    private TextView textView_highTemperature;
    private TextView textView_chanceOfPrecipitation;
    private TextView textView_windSpeed;
    private TextView textView_humidity;
    private ImageView imageView;

    @Override
    protected void onStart() {
        super.onStart();
        imageView = (ImageView) findViewById(R.id.imageView);
        textView_cityName = (TextView) findViewById(R.id.textView_city);
        textView_cityTemperature = (TextView) findViewById(R.id.textView_temperature);
        textView_lowTemperature = (TextView) findViewById(R.id.textView_low_temperature);
        textView_highTemperature = (TextView) findViewById(R.id.textView__high_temperature);
        textView_chanceOfPrecipitation = (TextView) findViewById(R.id.textView_precipitation);
        textView_windSpeed = (TextView) findViewById(R.id.textView_wind);
        textView_humidity = (TextView) findViewById(R.id.textView_humidity);

        imageView.setImageBitmap(bitmap);
        textView_cityName.setText(cityName);
        textView_cityTemperature.setText("Current temperature: " + cityTemperature);
        textView_lowTemperature.setText("Low temperature: " + lowTemperature);
        textView_highTemperature.setText("High temperature: " +  highTemperature);
        textView_chanceOfPrecipitation.setText("Precipitation: " + chanceOfPrecipitation);
        textView_windSpeed.setText("Wind speed: " + windSpeed);
        textView_humidity.setText("Humidity: " +  humidity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        bitmap = (Bitmap)this.getIntent().getParcelableExtra(RowItem.DRAWABLE);
        cityName = intent.getStringExtra(RowItem.CITY_NAME);
        cityTemperature = intent.getStringExtra(RowItem.TEMP);
        lowTemperature = intent.getStringExtra(RowItem.LOW_TEMP);
        highTemperature = intent.getStringExtra(RowItem.HIGH_TEMP);
        chanceOfPrecipitation = intent.getStringExtra(RowItem.PRECIPITATION);
        windSpeed = intent.getStringExtra(RowItem.WIND_SPEED);
        humidity = intent.getStringExtra(RowItem.HUMIDITY);


    }


}
