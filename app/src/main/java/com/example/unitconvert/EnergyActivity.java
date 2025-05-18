package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class EnergyActivity extends AppCompatActivity {

    private EditText inputValue, textResult;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;

    private final String[] energyUnits = {
            "Joule", "Kilojoule", "Calorie", "Kilocalorie", "Kilowatt-hour", "BTU"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energy_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        inputValue.setShowSoftInputOnFocus(false);  // Disable default soft keyboard

        // Set up spinners for energy units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, energyUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Set up custom keypad
        setUpCustomKeypad();

        buttonConvert.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                Toast.makeText(EnergyActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double convertedValue = convertEnergy(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", convertedValue, toUnit));
            } catch (NumberFormatException e) {
                Toast.makeText(EnergyActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCustomKeypad() {
        int[] buttonIds = new int[] {
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

        // Set the click listeners for each button on the custom keypad
        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertEnergy(double value, String fromUnit, String toUnit) {
        double valueInJoules = 0;

        // Convert to Joules (intermediate unit)
        switch (fromUnit) {
            case "Joule":
                valueInJoules = value;
                break;
            case "Kilojoule":
                valueInJoules = value * 1000;
                break;
            case "Calorie":
                valueInJoules = value * 4.184;
                break;
            case "Kilocalorie":
                valueInJoules = value * 4184;
                break;
            case "Kilowatt-hour":
                valueInJoules = value * 3.6e6;
                break;
            case "BTU":
                valueInJoules = value * 1055.06;
                break;
            default:
                Toast.makeText(this, "Unknown 'from' unit", Toast.LENGTH_SHORT).show();
                return 0;
        }

        // Convert from Joules to the target unit
        switch (toUnit) {
            case "Joule":
                return valueInJoules;
            case "Kilojoule":
                return valueInJoules / 1000;
            case "Calorie":
                return valueInJoules / 4.184;
            case "Kilocalorie":
                return valueInJoules / 4184;
            case "Kilowatt-hour":
                return valueInJoules / 3.6e6;
            case "BTU":
                return valueInJoules / 1055.06;
            default:
                Toast.makeText(this, "Unknown 'to' unit", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
