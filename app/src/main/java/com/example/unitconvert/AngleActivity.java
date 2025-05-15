package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AngleActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] angleUnits = {
            "Degree", "Radian", "Gradian", "Minutes", "Seconds"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angle_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

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

                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertAngle(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertAngle(double value, String fromUnit, String toUnit) {
        double valueInDegrees = 0;

        if (fromUnit.equals("Degree"))
        {
            valueInDegrees = value;
        }
        else if (fromUnit.equals("Radian"))
        {
            valueInDegrees = Math.toDegrees(value);
        }
        else if (fromUnit.equals("Gradian"))
        {
            valueInDegrees = value * 0.9;
        }
        else if (fromUnit.equals("Minutes"))
        {
            valueInDegrees = value / 60;
        }
        else if (fromUnit.equals("Seconds"))
        {
            valueInDegrees = value / 3600;
        }

        double result = 0;

        if (toUnit.equals("Degree"))
        {
            result = valueInDegrees;
        }
        else if (toUnit.equals("Radian"))
        {
            result = Math.toRadians(valueInDegrees);
        }
        else if (toUnit.equals("Gradian"))
        {
            result = valueInDegrees / 0.9;
        }
        else if (toUnit.equals("Minutes"))
        {
            result = valueInDegrees * 60;
        }
        else if (toUnit.equals("Seconds"))
        {
            result = valueInDegrees * 3600;
        }

        return result;
    }
}
