package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class FrequencyActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private final String[] frequencyUnits = {
            "Hertz", "Kilohertz", "Megahertz", "Gigahertz"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        // Disable the default keyboard
        inputValue.setShowSoftInputOnFocus(false);

        // Set up the adapter for the unit spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, frequencyUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Set up the custom keypad
        setUpCustomKeypad(inputValue);

        buttonConvert.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                Toast.makeText(FrequencyActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertFrequency(value, fromUnit, toUnit);
                textResult.setText(String.format("%.6f %s", result, toUnit));
            } catch (NumberFormatException e) {
                Toast.makeText(FrequencyActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
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

    private double convertFrequency(double value, String fromUnit, String toUnit) {
        double valueInHz = 0;

        // Convert to Hertz (base unit)
        switch (fromUnit) {
            case "Hertz":
                valueInHz = value;
                break;
            case "Kilohertz":
                valueInHz = value * 1_000;
                break;
            case "Megahertz":
                valueInHz = value * 1_000_000;
                break;
            case "Gigahertz":
                valueInHz = value * 1_000_000_000;
                break;
            default:
                Toast.makeText(this, "Unknown 'from' unit", Toast.LENGTH_SHORT).show();
                return 0;
        }

        // Convert from Hertz to target unit
        switch (toUnit) {
            case "Hertz":
                return valueInHz;
            case "Kilohertz":
                return valueInHz / 1_000;
            case "Megahertz":
                return valueInHz / 1_000_000;
            case "Gigahertz":
                return valueInHz / 1_000_000_000;
            default:
                Toast.makeText(this, "Unknown 'to' unit", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
