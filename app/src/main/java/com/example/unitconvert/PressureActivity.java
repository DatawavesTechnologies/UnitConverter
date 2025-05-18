package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class PressureActivity extends AppCompatActivity {

    private final String[] pressureUnits = {
            "Pascal", "Bar", "PSI", "Atmosphere", "Torr", "Millimeters of mercury"
    };

    private EditText inputValue, resultText;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pressure_activity);

        inputValue = findViewById(R.id.inputValue);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);
        resultText = findViewById(R.id.resultText);
        inputValue.setShowSoftInputOnFocus(false); // Disable default system keyboard

        // Set up adapter for pressure units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, pressureUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set up the custom keypad
        setUpCustomKeypad();

        convertButton.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString().trim();

            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertPressure(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));

            } catch (NumberFormatException e) {
                resultText.setText("Invalid number format.");
            }
        });
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

        // Set listener for keypad buttons
        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertPressure(double value, String fromUnit, String toUnit) {
        double valueInPascals;

        // Convert to Pascal (Pa)
        switch (fromUnit) {
            case "Pascal":
                valueInPascals = value;
                break;
            case "Bar":
                valueInPascals = value * 100000;
                break;
            case "PSI":
                valueInPascals = value * 6894.76;
                break;
            case "Atmosphere":
                valueInPascals = value * 101325;
                break;
            case "Torr":
            case "Millimeters of mercury":
                valueInPascals = value * 133.322;
                break;
            default:
                Toast.makeText(this, "Unknown from-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }

        // Convert from Pascal to target unit
        switch (toUnit) {
            case "Pascal":
                return valueInPascals;
            case "Bar":
                return valueInPascals / 100000;
            case "PSI":
                return valueInPascals / 6894.76;
            case "Atmosphere":
                return valueInPascals / 101325;
            case "Torr":
            case "Millimeters of mercury":
                return valueInPascals / 133.322;
            default:
                Toast.makeText(this, "Unknown to-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
