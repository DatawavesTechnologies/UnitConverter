package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LengthActivity extends AppCompatActivity {

    String[] lengthUnits = {
            "Millimeter", "Centimeter", "Meter", "Kilometer",
            "Inch", "Foot", "Yard", "Mile"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.length_activity);

        EditText inputValue = findViewById(R.id.inputValue);
        Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lengthUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            double value = Double.parseDouble(inputStr);
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();

            double result = convertLength(value, fromUnit, toUnit);
            resultText.setText(String.format("%.4f %s", result, toUnit));
        });
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double valueInMeters = 0;

        // Convert from any unit to meters
        switch (fromUnit) {
            case "Millimeter":
                valueInMeters = value / 1000;
                break;
            case "Centimeter":
                valueInMeters = value / 100;
                break;
            case "Meter":
                valueInMeters = value;
                break;
            case "Kilometer":
                valueInMeters = value * 1000;
                break;
            case "Inch":
                valueInMeters = value * 0.0254;
                break;
            case "Foot":
                valueInMeters = value * 0.3048;
                break;
            case "Yard":
                valueInMeters = value * 0.9144;
                break;
            case "Mile":
                valueInMeters = value * 1609.344;
                break;
            default:
                valueInMeters = 0;
        }

        double result = 0;

        // Convert from meters to target unit
        switch (toUnit) {
            case "Millimeter":
                result = valueInMeters * 1000;
                break;
            case "Centimeter":
                result = valueInMeters * 100;
                break;
            case "Meter":
                result = valueInMeters;
                break;
            case "Kilometer":
                result = valueInMeters / 1000;
                break;
            case "Inch":
                result = valueInMeters / 0.0254;
                break;
            case "Foot":
                result = valueInMeters / 0.3048;
                break;
            case "Yard":
                result = valueInMeters / 0.9144;
                break;
            case "Mile":
                result = valueInMeters / 1609.344;
                break;
            default:
                result = 0;
        }

        return result;
    }
}

