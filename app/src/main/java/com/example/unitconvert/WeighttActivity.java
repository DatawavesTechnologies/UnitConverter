package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WeighttActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private TextView textResult;
    private Button convertButton;

    private final String[] units = {
            "Milligram", "Gram", "Kilogram", "Ton", "Pound", "Ounce"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weigjht_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        textResult = findViewById(R.id.textResult);
        convertButton = findViewById(R.id.convertButton);

        // Setup Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertWeightAndDisplay();
            }
        });
    }

    private void convertWeightAndDisplay() {
        String inputStr = inputValue.getText().toString().trim();
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double input;
        try {
            input = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromUnit = (String) spinnerFrom.getSelectedItem();
        String toUnit = (String) spinnerTo.getSelectedItem();

        double result = convertWeight(input, fromUnit, toUnit);
        textResult.setText(String.format("%.6f %s", result, toUnit));
    }

    private double convertWeight(double value, String fromUnit, String toUnit) {
        double valueInKg = 0;

        if (fromUnit.equals("Milligram")) {
            valueInKg = value / 1_000_000;
        } else if (fromUnit.equals("Gram")) {
            valueInKg = value / 1000;
        } else if (fromUnit.equals("Kilogram")) {
            valueInKg = value;
        } else if (fromUnit.equals("Ton")) {
            valueInKg = value * 1000;
        } else if (fromUnit.equals("Pound")) {
            valueInKg = value * 0.45359237;
        } else if (fromUnit.equals("Ounce")) {
            valueInKg = value * 0.02834952;
        }

        double result = 0;
        if (toUnit.equals("Milligram")) {
            result = valueInKg * 1_000_000;
        } else if (toUnit.equals("Gram")) {
            result = valueInKg * 1000;
        } else if (toUnit.equals("Kilogram")) {
            result = valueInKg;
        } else if (toUnit.equals("Ton")) {
            result = valueInKg / 1000;
        } else if (toUnit.equals("Pound")) {
            result = valueInKg / 0.45359237;
        } else if (toUnit.equals("Ounce")) {
            result = valueInKg / 0.02834952;
        }

        return result;
    }
}
