package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class TemperatureActivity extends AppCompatActivity {

    private EditText inputTemp, resultView;
    private Spinner fromSpinner, toSpinner;
    private Button convertBtn;

    private final String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_activity);

        inputTemp = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.textResult);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertBtn = findViewById(R.id.buttonConvert);

        inputTemp.setShowSoftInputOnFocus(false); // Disable system keyboard

        // Set adapter for both spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, temperatureUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Convert button logic
        convertBtn.setOnClickListener(v -> {
            String input = inputTemp.getText().toString().trim();
            if (input.isEmpty()) {
                resultView.setText("Please enter a temperature.");
                return;
            }

            double value;
            try {
                value = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                resultView.setText("Invalid number format.");
                return;
            }

            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();

            double result = convertTemperature(value, fromUnit, toUnit);
            resultView.setText(String.format("%.2f %s", result, toUnit));
        });

        setUpCustomKeypad(); // Set up custom input keypad
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
            String current = inputTemp.getText().toString();

            if (btnText.equals("⌫")) {
                if (!current.isEmpty()) {
                    inputTemp.setText(current.substring(0, current.length() - 1));
                    inputTemp.setSelection(inputTemp.getText().length());
                }
            } else {
                inputTemp.setText(current + btnText);
                inputTemp.setSelection(inputTemp.getText().length());
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        double celsius = 0;

        // Convert to Celsius first
        switch (fromUnit) {
            case "Celsius":
                celsius = value;
                break;
            case "Fahrenheit":
                celsius = (value - 32) * 5.0 / 9.0;
                break;
            case "Kelvin":
                celsius = value - 273.15;
                break;
        }

        // Then from Celsius to target
        switch (toUnit) {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return celsius * 9.0 / 5.0 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                return 0;
        }
    }
}
