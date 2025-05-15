package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    String[] dataUnits = {
            "Bit", "Byte", "Kilobyte", "Megabyte", "Gigabyte", "Terabyte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);

        EditText inputValue = findViewById(R.id.inputValue);
        Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dataUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    resultText.setText("Please enter a value.");
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertData(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertData(double value, String fromUnit, String toUnit) {
        double valueInBytes = 0;

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