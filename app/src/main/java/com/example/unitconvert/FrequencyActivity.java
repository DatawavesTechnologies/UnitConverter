package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class FrequencyActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] frequencyUnits = {"Hertz", "Kilohertz", "Megahertz", "Gigahertz"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, frequencyUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(FrequencyActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertFrequency(value, fromUnit, toUnit);
                textResult.setText(String.format("%.6f %s", result, toUnit));
            }
        });
    }

    private double convertFrequency(double value, String fromUnit, String toUnit) {
        double valueInHz = 0;

        if (fromUnit.equals("Hertz")) {
            valueInHz = value;
        } else if (fromUnit.equals("Kilohertz")) {
            valueInHz = value * 1000;
        } else if (fromUnit.equals("Megahertz")) {
            valueInHz = value * 1000000;
        } else if (fromUnit.equals("Gigahertz")) {
            valueInHz = value * 1000000000;
        }

        if (toUnit.equals("Hertz")) {
            return valueInHz;
        } else if (toUnit.equals("Kilohertz")) {
            return valueInHz / 1000;
        } else if (toUnit.equals("Megahertz")) {
            return valueInHz / 1000000;
        } else if (toUnit.equals("Gigahertz")) {
            return valueInHz / 1000000000;
        }

        return 0;
    }
}
