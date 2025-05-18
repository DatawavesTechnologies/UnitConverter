package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class PowerActivity extends AppCompatActivity {

    String[] powerUnits = {"Watt", "Kilowatt", "Horsepower", "Megawatt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_activity);

        final EditText inputValue = findViewById(R.id.inputValue);
        final Spinner fromSpinner = findViewById(R.id.spinnerFrom);
        final Spinner toSpinner = findViewById(R.id.spinnerTo);
        final Button convertButton = findViewById(R.id.buttonConvert);
        final EditText resultText = findViewById(R.id.resultText);
        inputValue.setShowSoftInputOnFocus(false);  // Disable default system keyboard

        // Set up the adapter for the power units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, powerUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set up the custom keypad
        setUpCustomKeypad(inputValue);

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

                double result = convertPower(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));

            } catch (NumberFormatException e) {
                resultText.setText("Invalid number format.");
            }
        });
    }

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

    private double convertPower(double value, String fromUnit, String toUnit) {
        double valueInWatts = 0;

        // Convert input to watts
        if (fromUnit.equals("Watt")) {
            valueInWatts = value;
        } else if (fromUnit.equals("Kilowatt")) {
            valueInWatts = value * 1000;
        } else if (fromUnit.equals("Horsepower")) {
            valueInWatts = value * 745.699872;
        } else if (fromUnit.equals("Megawatt")) {
            valueInWatts = value * 1000000;
        }

        // Convert from watts to target unit
        double result = 0;
        if (toUnit.equals("Watt")) {
            result = valueInWatts;
        } else if (toUnit.equals("Kilowatt")) {
            result = valueInWatts / 1000;
        } else if (toUnit.equals("Horsepower")) {
            result = valueInWatts / 745.699872;
        } else if (toUnit.equals("Megawatt")) {
            result = valueInWatts / 1000000;
        }
        return result;
    }
}
