package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    private EditText inputValue, resultText;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;

    // Define your data units
    private String[] dataUnits = {
            "Bit", "Byte", "Kilobyte", "Megabyte", "Gigabyte", "Terabyte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);

        inputValue = findViewById(R.id.inputValue);
        resultText = findViewById(R.id.resultText);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);

        // Disable the default soft input keyboard (to use custom keyboard)
        inputValue.setShowSoftInputOnFocus(false);

        // Setup the spinners with the data units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dataUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set up the convert button click listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    resultText.setText("Please enter a value.");
                    return;
                }

                try {
                    double value = Double.parseDouble(inputStr);
                    String fromUnit = fromSpinner.getSelectedItem().toString();
                    String toUnit = toSpinner.getSelectedItem().toString();

                    // Perform the conversion
                    double result = convertData(value, fromUnit, toUnit);
                    resultText.setText(String.format("%.4f %s", result, toUnit));
                } catch (NumberFormatException e) {
                    resultText.setText("Invalid number");
                }
            }
        });

        // Set up the custom numeric keypad
        setUpCustomKeypad();
    }

    // Method to set up custom numeric keypad
    private void setUpCustomKeypad() {
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDot, R.id.buttonDelete
        };

        View.OnClickListener keyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                String btnText = btn.getText().toString();
                String current = inputValue.getText().toString();

                // Handle backspace/delete
                if (btnText.equals("⌫")) {
                    if (!current.isEmpty()) {
                        inputValue.setText(current.substring(0, current.length() - 1));
                        inputValue.setSelection(inputValue.getText().length());
                    }
                } else {
                    // Append the button text to the EditText input field
                    inputValue.setText(current + btnText);
                    inputValue.setSelection(inputValue.getText().length());
                }
            }
        };

        // Set onClick listeners for the custom numeric keypad buttons
        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    // Data conversion logic (similar to the Angle conversion)
    private double convertData(double value, String fromUnit, String toUnit) {
        double valueInBytes = 0;

        // Convert the input value to bytes
        switch (fromUnit) {
            case "Bit":
                valueInBytes = value / 8;
                break;
            case "Byte":
                valueInBytes = value;
                break;
            case "Kilobyte":
                valueInBytes = value * 1024;
                break;
            case "Megabyte":
                valueInBytes = value * 1024 * 1024;
                break;
            case "Gigabyte":
                valueInBytes = value * 1024 * 1024 * 1024;
                break;
            case "Terabyte":
                valueInBytes = value * 1024L * 1024 * 1024 * 1024;
                break;
        }

        // Convert the value in bytes to the target unit
        double result = 0;
        switch (toUnit) {
            case "Bit":
                result = valueInBytes * 8;
                break;
            case "Byte":
                result = valueInBytes;
                break;
            case "Kilobyte":
                result = valueInBytes / 1024;
                break;
            case "Megabyte":
                result = valueInBytes / (1024 * 1024);
                break;
            case "Gigabyte":
                result = valueInBytes / (1024 * 1024 * 1024);
                break;
            case "Terabyte":
                result = valueInBytes / (1024D * 1024 * 1024 * 1024);
                break;
        }

        return result;
    }
}
