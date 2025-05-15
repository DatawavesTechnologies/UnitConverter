package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedActivity extends AppCompatActivity {

    String[] speedUnits = {
            "Meters per second",
            "Kilometers per hour",
            "Miles per hour",
            "Feet per second",
            "Knots"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_activity);

        final EditText inputSpeed = findViewById(R.id.inputSpeed);
        final Spinner fromSpinner = findViewById(R.id.fromSpeedUnit);
        final Spinner toSpinner = findViewById(R.id.toSpeedUnit);
        final Button convertBtn = findViewById(R.id.convertSpeedBtn);
        final TextView resultView = findViewById(R.id.speedResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, speedUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputSpeed.getText().toString().trim();
                if (input.isEmpty()) {
                    resultView.setText("Please enter a speed value.");
                    return;
                }

                double value = Double.parseDouble(input);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertSpeed(value, fromUnit, toUnit);
                resultView.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertSpeed(double value, String fromUnit, String toUnit) {
        double valueInMps = 0;

        if (fromUnit.equals("Meters per second"))
        {
            valueInMps = value;
        } else if (fromUnit.equals("Kilometers per hour"))
        {
            valueInMps = value / 3.6;
        } else if (fromUnit.equals("Miles per hour"))
        {
            valueInMps = value * 0.44704;
        }
        else if (fromUnit.equals("Feet per second"))
        {
            valueInMps = value * 0.3048;
        }
        else if (fromUnit.equals("Knots")) {
            valueInMps = value * 0.514444;
        }

        double result = 0;
        if (toUnit.equals("Meters per second"))
        {
            result = valueInMps;
        }
        else if (toUnit.equals("Kilometers per hour"))
        {
            result = valueInMps * 3.6;
        }
        else if (toUnit.equals("Miles per hour"))
        {
            result = valueInMps / 0.44704;
        }
        else if (toUnit.equals("Feet per second"))
        {
            result = valueInMps / 0.3048;
        }
        else if (toUnit.equals("Knots"))
        {
            result = valueInMps / 0.514444;
        }

        return result;
    }
}
