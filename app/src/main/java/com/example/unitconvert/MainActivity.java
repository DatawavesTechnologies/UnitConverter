package com.example.unitconvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Equivalent to enableEdgeToEdge() in Kotlin
        setContentView(R.layout.activity_main);

        LinearLayout currencyButton = findViewById(R.id.currency);
        LinearLayout areaButton = findViewById(R.id.area);
        LinearLayout dataButton = findViewById(R.id.data);
        LinearLayout mileageButton = findViewById(R.id.millege);
        LinearLayout lengthButton = findViewById(R.id.lentgh);
        LinearLayout powerButton = findViewById(R.id.power);
        LinearLayout pressureButton = findViewById(R.id.pressure);
        LinearLayout speedButton = findViewById(R.id.speed);
        LinearLayout temperatureButton = findViewById(R.id.temperature);
        LinearLayout timeButton = findViewById(R.id.time);
        LinearLayout volumeButton = findViewById(R.id.volume);
        LinearLayout weightButton = findViewById(R.id.weight);
        LinearLayout energyButton = findViewById(R.id.energy);
        LinearLayout angleButton = findViewById(R.id.angle);
        LinearLayout clothingButton = findViewById(R.id.clothing);
        LinearLayout digitalButton = findViewById(R.id.DIU);
        LinearLayout cookingButton = findViewById(R.id.cooking);
        LinearLayout frequencyButton= findViewById(R.id.frequency);
        LinearLayout screenButton = findViewById(R.id.screen);
        LinearLayout footButton = findViewById(R.id.foot);

        currencyButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, Currencyconvert.class);
            startActivity(tent);
        });

        areaButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, AreaActivity.class);
            startActivity(tent);
        });

        dataButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, DataActivity.class);
            startActivity(tent);
        });

        mileageButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, MileageActivity.class);
            startActivity(tent);
        });

        lengthButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, LengthActivity.class);
            startActivity(tent);
        });

        pressureButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, PressureActivity.class);
            startActivity(tent);
        });

        powerButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, PowerActivity.class);
            startActivity(tent);
        });

        speedButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, SpeedActivity.class);
            startActivity(tent);
        });

        temperatureButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, TemperatureActivity.class);
            startActivity(tent);
        });

        timeButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, TimeActivity.class);
            startActivity(tent);
        });

        volumeButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, VolumeActivity.class);
            startActivity(tent);
        });

        weightButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this, WeighttActivity.class);
            startActivity(tent);
        });

        energyButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,EnergyActivity.class);
            startActivity(tent);
        });

        angleButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,AngleActivity.class);
            startActivity(tent);
        });

        clothingButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,ClothingActivity.class);
            startActivity(tent);
        });

        cookingButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,CookingActivity.class);
            startActivity(tent);
        });

        screenButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,ScreenActivity.class);
            startActivity(tent);
        });

        footButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,ShoeActivity.class);
            startActivity(tent);
        });

        frequencyButton.setOnClickListener(v -> {
            Intent tent = new Intent(MainActivity.this,FrequencyActivity.class);
            startActivity(tent);
        });
    }
}
