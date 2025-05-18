package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class CookingActivity extends AppCompatActivity {

    private EditText inputValue;
    private EditText outputValue; // This is etAmountTo in XML
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;

    private final String[] cookingUnits = {
            "Teaspoon", "Tablespoon", "Cup", "Ounce (fl oz)",
            "Milliliter", "Liter", "Gram", "Pound"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooking_activity);

        inputValue = findViewById(R.id.inputValue);
        outputValue = findViewById(R.id.etAmountTo);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);

        // Disable the soft keyboard
        inputValue.setShowSoftInputOnFocus(false);

        // Set up spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cookingUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Handle conversion
        buttonConvert.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString();
            if (inputStr.isEmpty()) {
                Toast.makeText(CookingActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertCooking(value, fromUnit, toUnit);
                outputValue.setText(String.format("%.4f", result));
            } catch (NumberFormatException e) {
                Toast.makeText(CookingActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
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

    private double convertCooking(double value, String fromUnit, String toUnit) {
        double valueInMl;

        switch (fromUnit) {
            case "Teaspoon":
                valueInMl = value * 4.92892;
                break;
            case "Tablespoon":
                valueInMl = value * 14.7868;
                break;
            case "Cup":
                valueInMl = value * 240;
                break;
            case "Ounce (fl oz)":
                valueInMl = value * 29.5735;
                break;
            case "Milliliter":
                valueInMl = value;
                break;
            case "Liter":
                valueInMl = value * 1000;
                break;
            case "Gram":
                valueInMl = value; // Assumes 1g = 1ml for water-based substances
                break;
            case "Pound":
                valueInMl = value * 453.592;
                break;
            default:
                return 0;
        }

        switch (toUnit) {
            case "Teaspoon":
                return valueInMl / 4.92892;
            case "Tablespoon":
                return valueInMl / 14.7868;
            case "Cup":
                return valueInMl / 240;
            case "Ounce (fl oz)":
                return valueInMl / 29.5735;
            case "Milliliter":
                return valueInMl;
            case "Liter":
                return valueInMl / 1000;
            case "Gram":
                return valueInMl;
            case "Pound":
                return valueInMl / 453.592;
            default:
                return 0;
        }
    }
}
