package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class ScreenActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] screenUnits = {
            "Pixels", "Density-independent Pixels", "Inches",
            "Millimeters", "Centimeters", "Points"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, screenUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(ScreenActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertScreenUnit(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertScreenUnit(double value, String fromUnit, String toUnit) {
        double dpi = 160; // baseline screen density
        double valueInInches = 0;

        // Convert from source unit to inches
        if (fromUnit.equals("Pixels")) {
            valueInInches = value / dpi;
        } else if (fromUnit.equals("Density-independent Pixels")) {
            valueInInches = (value * (dpi / 160)) / dpi;
        } else if (fromUnit.equals("Inches")) {
            valueInInches = value;
        } else if (fromUnit.equals("Millimeters")) {
            valueInInches = value / 25.4;
        } else if (fromUnit.equals("Centimeters")) {
            valueInInches = value / 2.54;
        } else if (fromUnit.equals("Points")) {
            valueInInches = value / 72.0;
        }

        // Convert from inches to target unit
        double result = 0;
        if (toUnit.equals("Pixels")) {
            result = valueInInches * dpi;
        } else if (toUnit.equals("Density-independent Pixels")) {
            result = (valueInInches * dpi) * (160 / dpi);
        } else if (toUnit.equals("Inches")) {
            result = valueInInches;
        } else if (toUnit.equals("Millimeters")) {
            result = valueInInches * 25.4;
        } else if (toUnit.equals("Centimeters")) {
            result = valueInInches * 2.54;
        } else if (toUnit.equals("Points")) {
            result = valueInInches * 72.0;
        }

        return result;
    }
}