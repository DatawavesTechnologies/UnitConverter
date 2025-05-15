package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MileageActivity extends AppCompatActivity {

    String[] fuelUnits = {
            "Kilometers per liter",
            "Liters per 100 km",
            "Miles per gallon (US)",
            "Miles per gallon (UK)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_activity);

        EditText inputValue = findViewById(R.id.inputValue);
        Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuelUnits);
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

                double result = convertFuelConsumption(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertFuelConsumption(double value, String fromUnit, String toUnit) {
        double kmPerLiter = 0;

        switch (fromUnit) {
            case "Kilometers per liter":
                kmPerLiter = value;
                break;
            case "Liters per 100 km":
                kmPerLiter = 100 / value;
                break;
            case "Miles per gallon (US)":
                kmPerLiter = value * 0.425144;
                break;
            case "Miles per gallon (UK)":
                kmPerLiter = value * 0.354006;
                break;
        }

        double result = 0;
        switch (toUnit) {
            case "Kilometers per liter":
                result = kmPerLiter;
                break;
            case "Liters per 100 km":
                result = 100 / kmPerLiter;
                break;
            case "Miles per gallon (US)":
                result = kmPerLiter / 0.425144;
                break;
            case "Miles per gallon (UK)":
                result = kmPerLiter / 0.354006;
                break;
        }

        return result;
    }
}
