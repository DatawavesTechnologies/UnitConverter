package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MileageActivity extends AppCompatActivity {

    private final String[] fuelUnits = {
            "Kilometers per liter",
            "Liters per 100 km",
            "Miles per gallon (US)",
            "Miles per gallon (UK)"
    };

    private EditText inputValue;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_activity);

        inputValue = findViewById(R.id.inputValue);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.buttonConvert);
        resultText = findViewById(R.id.resultText);
        inputValue.setShowSoftInputOnFocus(false);  // Disable the default soft keyboard

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, fuelUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Set up the custom keypad
        setUpCustomKeypad(inputValue);

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

                    double result = convertFuelConsumption(value, fromUnit, toUnit);
                    resultText.setText(String.format("%.4f %s", result, toUnit));
                } catch (NumberFormatException e) {
                    resultText.setText("Invalid number format.");
                }
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

    private double convertFuelConsumption(double value, String fromUnit, String toUnit) {
        double valueInKmPerLiter;

        // Convert all units to km/l as the base unit
        switch (fromUnit) {
            case "Kilometers per liter":
                valueInKmPerLiter = value;
                break;
            case "Liters per 100 km":
                valueInKmPerLiter = 100 / value;
                break;
            case "Miles per gallon (US)":
                valueInKmPerLiter = value * 0.425144;
                break;
            case "Miles per gallon (UK)":
                valueInKmPerLiter = value * 0.354006;
                break;
            default:
                Toast.makeText(this, "Unknown from-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }

        // Convert from base unit to target unit
        switch (toUnit) {
            case "Kilometers per liter":
                return valueInKmPerLiter;
            case "Liters per 100 km":
                return 100 / valueInKmPerLiter;
            case "Miles per gallon (US)":
                return valueInKmPerLiter / 0.425144;
            case "Miles per gallon (UK)":
                return valueInKmPerLiter / 0.354006;
            default:
                Toast.makeText(this, "Unknown to-unit", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
