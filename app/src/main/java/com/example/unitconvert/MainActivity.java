package com.example.unitconvert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupClick(R.id.currency, "currency");
        setupClick(R.id.area, "area");
        setupClick(R.id.data, "data");
        setupClick(R.id.millege, "mileage");
        setupClick(R.id.lentgh, "length");
        setupClick(R.id.power, "power");
        setupClick(R.id.pressure, "pressure");
        setupClick(R.id.speed, "speed");
        setupClick(R.id.temperature, "temperature");
        setupClick(R.id.time, "time");
        setupClick(R.id.volume, "volume");
        setupClick(R.id.weight, "weight");
        setupClick(R.id.energy, "energy");
        setupClick(R.id.angle, "angle");
        setupClick(R.id.clothing, "clothing");
        setupClick(R.id.DIU, "digital");
        setupClick(R.id.cooking, "cooking");
        setupClick(R.id.frequency, "frequency");
        setupClick(R.id.screen, "screen");
        setupClick(R.id.foot, "foot");
    }

    private void setupClick(int viewId, String unitType) {
        LinearLayout layout = findViewById(viewId);
        layout.setTag(unitType);
        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String unitType = (String) view.getTag();
        Intent intent = new Intent(MainActivity.this, ConvertActivity.class);
        intent.putExtra("unit_type", unitType); // Pass the selected unit type
        startActivity(intent);
    }
}
