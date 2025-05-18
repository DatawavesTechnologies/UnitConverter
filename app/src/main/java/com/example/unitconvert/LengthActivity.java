package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class LengthActivity extends AppCompatActivity {

    private final String[] lengthUnits = {
            "Millimeter", "Centimeter", "Meter", "Kilometer",
            "Inch", "Foot", "Yard", "Mile"
    };

    private EditText inputValue, resultText;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.length_activity);

        inputValue = findViewById(R.id.inputValue);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);
        resultText = findViewById(R.id.resultText);

        // Disable the default keyboard
        inputValue.setShowSoftInputOnFocus(false);

        // Set up the adapter for the unit spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, lengthUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set up the custom keypad
        setUpCustomKeypad(inputValue);

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

                double result = convertLength(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            } catch (NumberFormatException e) {
                resultText.setText("Invalid number format.");
            }
        });
    }

    // Custom keypad setup
    private void setUpCustomKeypad(final EditText inputValue) {
        // Define the numeric button IDs
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDelete
        };

        // OnClickListener for keypad buttons
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

        // Set the listener for the keypad buttons
        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double valueInMeters = 0;

        // Convert to base unit (Meters)
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
                Toast.makeText(this, "Unknown from-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }

        // Convert from base unit (Meters) to target unit
        switch (toUnit) {
            case "Millimeter":
                return valueInMeters * 1000;
            case "Centimeter":
                return valueInMeters * 100;
            case "Meter":
                return valueInMeters;
            case "Kilometer":
                return valueInMeters / 1000;
            case "Inch":
                return valueInMeters / 0.0254;
            case "Foot":
                return valueInMeters / 0.3048;
            case "Yard":
                return valueInMeters / 0.9144;
            case "Mile":
                return valueInMeters / 1609.344;
            default:
                Toast.makeText(this, "Unknown to-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
