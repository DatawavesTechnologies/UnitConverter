package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedActivity extends AppCompatActivity {

    private EditText inputSpeed, resultView;
    private Spinner fromSpinner, toSpinner;
    private Button convertBtn;

    private final String[] speedUnits = {
            "Meters per second",
            "Kilometers per hour",
            "Miles per hour",
            "Feet per second",
            "Knots"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_activity);

        inputSpeed = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.textResult);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertBtn = findViewById(R.id.buttonConvert);

        inputSpeed.setShowSoftInputOnFocus(false); // Disable system keyboard

        // Set adapter for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, speedUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Convert button click
        convertBtn.setOnClickListener(v -> {
            String input = inputSpeed.getText().toString().trim();
            if (input.isEmpty()) {
                resultView.setText("Please enter a speed value.");
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

            double result = convertSpeed(value, fromUnit, toUnit);
            resultView.setText(String.format("%.4f %s", result, toUnit));
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
            String current = inputSpeed.getText().toString();

            if (btnText.equals("⌫")) {
                if (!current.isEmpty()) {
                    inputSpeed.setText(current.substring(0, current.length() - 1));
                    inputSpeed.setSelection(inputSpeed.getText().length());
                }
            } else {
                inputSpeed.setText(current + btnText);
                inputSpeed.setSelection(inputSpeed.getText().length());
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertSpeed(double value, String fromUnit, String toUnit) {
        double valueInMps = 0;

        // Convert to meters per second
        switch (fromUnit) {
            case "Meters per second":
                valueInMps = value;
                break;
            case "Kilometers per hour":
                valueInMps = value / 3.6;
                break;
            case "Miles per hour":
                valueInMps = value * 0.44704;
                break;
            case "Feet per second":
                valueInMps = value * 0.3048;
                break;
            case "Knots":
                valueInMps = value * 0.514444;
                break;
        }

        // Convert from meters per second to target unit
        switch (toUnit) {
            case "Meters per second":
                return valueInMps;
            case "Kilometers per hour":
                return valueInMps * 3.6;
            case "Miles per hour":
                return valueInMps / 0.44704;
            case "Feet per second":
                return valueInMps / 0.3048;
            case "Knots":
                return valueInMps / 0.514444;
            default:
                return 0;
        }
    }
}
