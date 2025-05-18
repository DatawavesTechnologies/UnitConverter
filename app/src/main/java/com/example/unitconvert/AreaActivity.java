package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class AreaActivity extends AppCompatActivity {

    private EditText inputValue, resultText;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;

    private final String[] areaUnits = {
            "Square Millimeter", "Square Centimeter", "Square Meter", "Square Kilometer",
            "Square Inch", "Square Foot", "Square Yard", "Acre", "Hectare"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_activity);

        inputValue = findViewById(R.id.inputValue);
        resultText = findViewById(R.id.etAmountTo);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);

        // Disable default soft keyboard
        inputValue.setShowSoftInputOnFocus(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, areaUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                Toast.makeText(AreaActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();
                double result = convertArea(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f", result));
            } catch (NumberFormatException e) {
                Toast.makeText(AreaActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
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

        switch (toUnit) {
            case "Square Millimeter":
                return valueInSquareMeters * 1_000_000;
            case "Square Centimeter":
                return valueInSquareMeters * 10_000;
            case "Square Meter":
                return valueInSquareMeters;
            case "Square Kilometer":
                return valueInSquareMeters / 1_000_000;
            case "Square Inch":
                return valueInSquareMeters / 0.00064516;
            case "Square Foot":
                return valueInSquareMeters / 0.092903;
            case "Square Yard":
                return valueInSquareMeters / 0.836127;
            case "Acre":
                return valueInSquareMeters / 4046.856;
            case "Hectare":
                return valueInSquareMeters / 10_000;
        }

        return 0;
    }
}
