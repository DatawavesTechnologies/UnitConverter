package com.example.unitconvert;


import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class TimeActivity extends AppCompatActivity {

    String[] timeUnits = {
            "Milliseconds", "Seconds", "Minutes", "Hours", "Days",
            "Weeks", "Months", "Years"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);

        final EditText inputTime = findViewById(R.id.inputTime);
        final Spinner fromSpinner = findViewById(R.id.fromTimeUnit);
        final Spinner toSpinner = findViewById(R.id.toTimeUnit);
        final Button convertBtn = findViewById(R.id.convertTimeBtn);
        final TextView resultView = findViewById(R.id.timeResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputTime.getText().toString().trim();
                if (input.isEmpty()) {
                    resultView.setText("Enter a value");
                    return;
                }

                double value = Double.parseDouble(input);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertTime(value, fromUnit, toUnit);
                resultView.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertTime(double value, String fromUnit, String toUnit) {
        double valueInSeconds = 0;

        if (fromUnit.equals("Milliseconds"))
        {
            valueInSeconds = value / 1000;
        }
        else if (fromUnit.equals("Seconds"))
        {
            valueInSeconds = value;
        }
        else if (fromUnit.equals("Minutes"))
        {
            valueInSeconds = value * 60;
        }
        else if (fromUnit.equals("Hours"))
        {
            valueInSeconds = value * 3600;
        }
        else if (fromUnit.equals("Days"))
        {
            valueInSeconds = value * 86400;
        }
        else if (fromUnit.equals("Weeks"))
        {
            valueInSeconds = value * 604800;
        }
        else if (fromUnit.equals("Months"))
        {
            valueInSeconds = value * 2.628e6;
        }
        else if (fromUnit.equals("Years"))
        {
            valueInSeconds = value * 3.154e7;
        }

        double result = 0;
        if (toUnit.equals("Milliseconds"))
        {
            result = valueInSeconds * 1000;
        }
        else if (toUnit.equals("Seconds"))
        {
            result = valueInSeconds;
        }
        else if (toUnit.equals("Minutes"))
        {
            result = valueInSeconds / 60;
        }
        else if (toUnit.equals("Hours"))
        {
            result = valueInSeconds / 3600;
        }
        else if (toUnit.equals("Days"))
        {
            result = valueInSeconds / 86400;
        }
        else if (toUnit.equals("Weeks"))
        {
            result = valueInSeconds / 604800;
        }
        else if (toUnit.equals("Months"))
        {
            result = valueInSeconds / 2.628e6;
        }
        else if (toUnit.equals("Years"))
        {
            result = valueInSeconds / 3.154e7;
        }

        return result;
    }
}
