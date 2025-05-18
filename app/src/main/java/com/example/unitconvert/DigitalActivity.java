package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DigitalActivity extends AppCompatActivity {

    private EditText inputValue, resultText;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;

    private final String[] imageUnits = {
            "Pixels", "Inches", "Centimeters", "Millimeters", "Points", "DPI", "PPI"
    };

    private static final double DEFAULT_PPI = 96.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digital_activity);

        inputValue = findViewById(R.id.inputValue);
        resultText = findViewById(R.id.resultText);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);

        // Disable soft keyboard to use custom keyboard
        inputValue.setShowSoftInputOnFocus(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, imageUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertImageUnits(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            } catch (NumberFormatException e) {
                resultText.setText("Invalid number");
            }
        });

        setUpCustomKeypad();
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

    private double convertImageUnits(double value, String fromUnit, String toUnit) {
        double inches = 0;

        // Step 1: Convert from any unit to inches
        switch (fromUnit) {
            case "Pixels":
                inches = value / DEFAULT_PPI;
                break;
            case "Inches":
                inches = value;
                break;
            case "Centimeters":
                inches = value / 2.54;
                break;
            case "Millimeters":
                inches = value / 25.4;
                break;
            case "Points":
                inches = value / 72.0;
                break;
            case "DPI":
            case "PPI":
                // DPI and PPI aren't directly convertible to inches without physical size or pixel count
                // So treat this as if user entered PPI or DPI value to convert to inches
                // We return that value unchanged in this context.
                return value;
        }

        // Step 2: Convert from inches to target unit
        switch (toUnit) {
            case "Pixels":
                return inches * DEFAULT_PPI;
            case "Inches":
                return inches;
            case "Centimeters":
                return inches * 2.54;
            case "Millimeters":
                return inches * 25.4;
            case "Points":
                return inches * 72.0;
            case "DPI":
            case "PPI":
                return DEFAULT_PPI;
        }

        return value;
    }
}
