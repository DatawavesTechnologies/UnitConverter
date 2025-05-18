package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AngleActivity extends AppCompatActivity {

    private EditText inputValue, outputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;

    private String[] angleUnits = {
            "Degree", "Radian", "Gradian", "Minutes", "Seconds"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angle_activity);

        inputValue = findViewById(R.id.inputValue);
        outputValue = findViewById(R.id.etAmountTo);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);

        // Disable the default soft keyboard
        inputValue.setShowSoftInputOnFocus(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, angleUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(AngleActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double value = Double.parseDouble(inputStr);
                    String fromUnit = spinnerFrom.getSelectedItem().toString();
                    String toUnit = spinnerTo.getSelectedItem().toString();
                    double result = convertAngle(value, fromUnit, toUnit);
                    String resultStr = String.format("%.4f", result);
                    outputValue.setText(resultStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AngleActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                }
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

        View.OnClickListener keyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }

    private double convertAngle(double value, String fromUnit, String toUnit) {
        double valueInDegrees;

        switch (fromUnit) {
            case "Degree":
                valueInDegrees = value;
                break;
            case "Radian":
                valueInDegrees = Math.toDegrees(value);
                break;
            case "Gradian":
                valueInDegrees = value * 0.9;
                break;
            case "Minutes":
                valueInDegrees = value / 60.0;
                break;
            case "Seconds":
                valueInDegrees = value / 3600.0;
                break;
            default:
                return 0;
        }

        switch (toUnit) {
            case "Degree":
                return valueInDegrees;
            case "Radian":
                return Math.toRadians(valueInDegrees);
            case "Gradian":
                return valueInDegrees / 0.9;
            case "Minutes":
                return valueInDegrees * 60.0;
            case "Seconds":
                return valueInDegrees * 3600.0;
            default:
                return 0;
        }
    }
}
