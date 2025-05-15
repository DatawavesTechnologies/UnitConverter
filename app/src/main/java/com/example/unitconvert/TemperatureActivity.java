package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class TemperatureActivity extends AppCompatActivity {

    String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_activity);

        final EditText inputTemp = findViewById(R.id.inputTemp);
        final Spinner fromSpinner = findViewById(R.id.fromTempUnit);
        final Spinner toSpinner = findViewById(R.id.toTempUnit);
        final Button convertBtn = findViewById(R.id.convertTempBtn);
        final TextView resultView = findViewById(R.id.tempResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, temperatureUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputTemp.getText().toString().trim();
                if (input.isEmpty()) {
                    resultView.setText("Please enter a temperature.");
                    return;
                }

                double value = Double.parseDouble(input);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertTemperature(value, fromUnit, toUnit);
                resultView.setText(String.format("%.2f %s", result, toUnit));
            }
        });
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        double celsius = 0;

        if (fromUnit.equals("Celsius")) {
            celsius = value;
        } else if (fromUnit.equals("Fahrenheit")) {
            celsius = (value - 32) * 5.0 / 9.0;
        } else if (fromUnit.equals("Kelvin")) {
            celsius = value - 273.15;
        }

        double result = 0;
        if (toUnit.equals("Celsius")) {
            result = celsius;
        } else if (toUnit.equals("Fahrenheit")) {
            result = celsius * 9.0 / 5.0 + 32;
        } else if (toUnit.equals("Kelvin")) {
            result = celsius + 273.15;
        }

        return result;
    }
}
