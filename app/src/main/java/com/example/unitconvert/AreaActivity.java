package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AreaActivity extends AppCompatActivity {

    String[] areaUnits = {
            "Square Millimeter", "Square Centimeter", "Square Meter", "Square Kilometer",
            "Square Inch", "Square Foot", "Square Yard", "Acre", "Hectare"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_activity);

        EditText inputValue = findViewById(R.id.inputValue);
        Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, areaUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    resultText.setText("Please enter a value.");
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertArea(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertArea(double value, String fromUnit, String toUnit) {
        double valueInSquareMeters = 0;

        switch (fromUnit) {
            case "Square Millimeter":
                valueInSquareMeters = value / 1_000_000;
                break;
            case "Square Centimeter":
                valueInSquareMeters = value / 10_000;
                break;
            case "Square Meter":
                valueInSquareMeters = value;
                break;
            case "Square Kilometer":
                valueInSquareMeters = value * 1_000_000;
                break;
            case "Square Inch":
                valueInSquareMeters = value * 0.00064516;
                break;
            case "Square Foot":
                valueInSquareMeters = value * 0.092903;
                break;
            case "Square Yard":
                valueInSquareMeters = value * 0.836127;
                break;
            case "Acre":
                valueInSquareMeters = value * 4046.856;
                break;
            case "Hectare":
                valueInSquareMeters = value * 10_000;
                break;
        }

        double result = 0;

        switch (toUnit) {
            case "Square Millimeter":
                result = valueInSquareMeters * 1_000_000;
                break;
            case "Square Centimeter":
                result = valueInSquareMeters * 10_000;
                break;
            case "Square Meter":
                result = valueInSquareMeters;
                break;
            case "Square Kilometer":
                result = valueInSquareMeters / 1_000_000;
                break;
            case "Square Inch":
                result = valueInSquareMeters / 0.00064516;
                break;
            case "Square Foot":
                result = valueInSquareMeters / 0.092903;
                break;
            case "Square Yard":
                result = valueInSquareMeters / 0.836127;
                break;
            case "Acre":
                result = valueInSquareMeters / 4046.856;
                break;
            case "Hectare":
                result = valueInSquareMeters / 10_000;
                break;
        }

        return result;
    }
}
