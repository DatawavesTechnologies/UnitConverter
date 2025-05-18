package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class VolumeActivity extends AppCompatActivity {

    private EditText inputVolume;
    private EditText resultView;
    private Spinner fromSpinner, toSpinner;

    private final String[] volumeUnits = {
            "Milliliter", "Liter", "Cubic meter", "Gallon (US)",
            "Quart", "Pint", "Cubic inch"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_activity);

        inputVolume = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.textResult);
        fromSpinner = findViewById(R.id.spinnerFrom);
        toSpinner = findViewById(R.id.spinnerTo);
        Button convertBtn = findViewById(R.id.buttonConvert);

        inputVolume.setShowSoftInputOnFocus(false); // Hide system keyboard

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, volumeUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        setUpCustomKeypad(); // <-- Call custom keypad setup

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputVolume.getText().toString().trim();
                if (input.isEmpty()) {
                    resultView.setText("Enter a value");
                    return;
                }

                double value;
                try {
                    value = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    resultView.setText("Invalid input");
                    return;
                }

                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertVolume(value, fromUnit, toUnit);
                resultView.setText(String.format("%.4f %s", result, toUnit));
            }
        });
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
            String current = inputVolume.getText().toString();

            if (btnText.equals("⌫")) {
                if (!current.isEmpty()) {
                    inputVolume.setText(current.substring(0, current.length() - 1));
                    inputVolume.setSelection(inputVolume.getText().length());
                }
            } else {
                inputVolume.setText(current + btnText);
                inputVolume.setSelection(inputVolume.getText().length());
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertVolume(double value, String fromUnit, String toUnit) {
        double valueInLiters = 0;

        switch (fromUnit) {
            case "Milliliter":
                valueInLiters = value / 1000;
                break;
            case "Liter":
                valueInLiters = value;
                break;
            case "Cubic meter":
                valueInLiters = value * 1000;
                break;
            case "Gallon (US)":
                valueInLiters = value * 3.78541;
                break;
            case "Quart":
                valueInLiters = value * 0.946353;
                break;
            case "Pint":
                valueInLiters = value * 0.473176;
                break;
            case "Cubic inch":
                valueInLiters = value * 0.0163871;
                break;
        }

        switch (toUnit) {
            case "Milliliter":
                return valueInLiters * 1000;
            case "Liter":
                return valueInLiters;
            case "Cubic meter":
                return valueInLiters / 1000;
            case "Gallon (US)":
                return valueInLiters / 3.78541;
            case "Quart":
                return valueInLiters / 0.946353;
            case "Pint":
                return valueInLiters / 0.473176;
            case "Cubic inch":
                return valueInLiters / 0.0163871;
            default:
                return 0;
        }
    }
}
