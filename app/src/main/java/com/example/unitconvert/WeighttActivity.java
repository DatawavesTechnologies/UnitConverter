package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

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
        setContentView(R.layout.weigjht_activity); // Note: Make sure XML file name is correct
        inputValue.setShowSoftInputOnFocus(false);
        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        textResult = findViewById(R.id.textResult);
        convertButton = findViewById(R.id.buttonConvert);

        // Set up spinner adapter
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
        setUpCustomKeypad();
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

        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();

        double result = convertWeight(input, fromUnit, toUnit);
        textResult.setText(String.format("%.6f %s", result, toUnit));

    }

    private void setUpCustomKeypad() {
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDot, R.id.buttonDelete
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
    private double convertWeight(double value, String fromUnit, String toUnit) {
        double valueInKg;

        // Convert to kilograms
        switch (fromUnit) {
            case "Milligram":
                valueInKg = value / 1_000_000;
                break;
            case "Gram":
                valueInKg = value / 1000;
                break;
            case "Kilogram":
                valueInKg = value;
                break;
            case "Ton":
                valueInKg = value * 1000;
                break;
            case "Pound":
                valueInKg = value * 0.45359237;
                break;
            case "Ounce":
                valueInKg = value * 0.02834952;
                break;
            default:
                valueInKg = 0;
        }

        // Convert from kilograms to target unit
        switch (toUnit) {
            case "Milligram":
                return valueInKg * 1_000_000;
            case "Gram":
                return valueInKg * 1000;
            case "Kilogram":
                return valueInKg;
            case "Ton":
                return valueInKg / 1000;
            case "Pound":
                return valueInKg / 0.45359237;
            case "Ounce":
                return valueInKg / 0.02834952;
            default:
                return 0;
        }
    }
}
