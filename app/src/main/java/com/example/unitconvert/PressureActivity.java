package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class PressureActivity extends AppCompatActivity {

    String[] pressureUnits = {
            "Pascal", "Bar", "PSI", "Atmosphere", "Torr", "Millimeters of mercury"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pressure_activity);

        final EditText inputValue = findViewById(R.id.inputValue);
        final Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        final Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        final Button convertButton = findViewById(R.id.convertButton);
        final TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pressureUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString().trim();
                if (inputStr.isEmpty()) {
                    resultText.setText("Please enter a value.");
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertPressure(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertPressure(double value, String fromUnit, String toUnit) {
        double valueInPa = 0;

        // Convert to Pascal
        switch (fromUnit) {
            case "Pascal":
                valueInPa = value;
                break;
            case "Bar":
                valueInPa = value * 100000;
                break;
            case "PSI":
                valueInPa = value * 6894.76;
                break;
            case "Atmosphere":
                valueInPa = value * 101325;
                break;
            case "Torr":
            case "Millimeters of mercury":
                valueInPa = value * 133.322;
                break;
            default:
                valueInPa = 0;
        }

        // Convert from Pascal to target unit
        double result = 0;
        switch (toUnit) {
            case "Pascal":
                result = valueInPa;
                break;
            case "Bar":
                result = valueInPa / 100000;
                break;
            case "PSI":
                result = valueInPa / 6894.76;
                break;
            case "Atmosphere":
                result = valueInPa / 101325;
                break;
            case "Torr":
            case "Millimeters of mercury":
                result = valueInPa / 133.322;
                break;
            default:
                result = 0;
        }

        return result;
    }
}
