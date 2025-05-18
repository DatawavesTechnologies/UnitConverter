package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ScreenActivity extends AppCompatActivity {

    private EditText inputValue, textResult;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;

    private final String[] screenUnits = {
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
        inputValue.setShowSoftInputOnFocus(false); // Disable default system keyboard

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, screenUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString().trim();
            if (inputStr.isEmpty()) {
                Toast.makeText(ScreenActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertScreenUnit(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", result, toUnit));
            } catch (NumberFormatException e) {
                Toast.makeText(ScreenActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
            }
        });

        setUpCustomKeypad(); // Set up custom input keypad
    }

    private void setUpCustomKeypad() {
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDelete
        };

        View.OnClickListener keyListener = v -> {
            Button btn = (Button) v;
            String btnText = btn.getText().toString();
            String current = inputValue.getText().toString();

            if (btnText.equals("⌫")) {
                if (!current.isEmpty()) {
                    inputValue.setText(current.substring(0, current.length() - 1));
                    inputValue.setSelection(inputValue.getText().length());
                }
            } else {
                inputValue.setText(current + btnText);
                inputValue.setSelection(inputValue.getText().length());
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertScreenUnit(double value, String fromUnit, String toUnit) {
        final double dpi = 160; // Base screen density for Android (mdpi)
        double valueInInches = 0;

        // Convert input to inches
        switch (fromUnit) {
            case "Pixels":
                valueInInches = value / dpi;
                break;
            case "Density-independent Pixels":
                valueInInches = value / 160;
                break;
            case "Inches":
                valueInInches = value;
                break;
            case "Millimeters":
                valueInInches = value / 25.4;
                break;
            case "Centimeters":
                valueInInches = value / 2.54;
                break;
            case "Points":
                valueInInches = value / 72.0;
                break;
        }

        // Convert inches to target unit
        switch (toUnit) {
            case "Pixels":
                return valueInInches * dpi;
            case "Density-independent Pixels":
                return valueInInches * 160;
            case "Inches":
                return valueInInches;
            case "Millimeters":
                return valueInInches * 25.4;
            case "Centimeters":
                return valueInInches * 2.54;
            case "Points":
                return valueInInches * 72.0;
            default:
                return 0;
        }
    }
}
