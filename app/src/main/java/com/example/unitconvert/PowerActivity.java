package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class PowerActivity extends AppCompatActivity {

    String[] powerUnits = {"Watt", "Kilowatt", "Horsepower", "Megawatt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_activity);

        final EditText inputValue = findViewById(R.id.inputValue);
        final Spinner fromSpinner = findViewById(R.id.fromUnitSpinner);
        final Spinner toSpinner = findViewById(R.id.toUnitSpinner);
        final Button convertButton = findViewById(R.id.convertButton);
        final TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, powerUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString().trim();
                if (inputStr.isEmpty()) {
                    resultText.setText("Please enter a value.");
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertPower(value, fromUnit, toUnit);
                resultText.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertPower(double value, String fromUnit, String toUnit) {
        double valueInWatts = 0;

        if (fromUnit.equals("Watt"))
        {
            valueInWatts = value;
        }
        else if (fromUnit.equals("Kilowatt"))
        {
            valueInWatts = value * 1000;
        }
        else if (fromUnit.equals("Horsepower"))
        {
            valueInWatts = value * 745.699872;
        }
        else if (fromUnit.equals("Megawatt"))
        {
            valueInWatts = value * 1000000;
        }

        double result = 0;
        if (toUnit.equals("Watt"))
        {
            result = valueInWatts;
        } else if (toUnit.equals("Kilowatt"))
        {
            result = valueInWatts / 1000;
        } else if (toUnit.equals("Horsepower"))
        {
            result = valueInWatts / 745.699872;
        } else if (toUnit.equals("Megawatt"))
        {
            result = valueInWatts / 1000000;
        }
        return result;
    }
}
