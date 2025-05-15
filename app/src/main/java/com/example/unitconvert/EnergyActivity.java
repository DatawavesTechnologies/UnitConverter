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

public class EnergyActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] energyUnits = {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, energyUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(EnergyActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double convertedValue = convertEnergy(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", convertedValue, toUnit));
            }
        });
    }

    private double convertEnergy(double value, String fromUnit, String toUnit) {
        double valueInJoules = 0;

        if (fromUnit.equals("Joule"))
        {
            valueInJoules = value;
        }
        else if (fromUnit.equals("Kilojoule"))
        {
            valueInJoules = value * 1000;
        }
        else if (fromUnit.equals("Calorie"))
        {
            valueInJoules = value * 4.184;
        }
        else if (fromUnit.equals("Kilocalorie"))
        {
            valueInJoules = value * 4184;
        }
        else if (fromUnit.equals("Kilowatt-hour"))
        {
            valueInJoules = value * 3.6e6;
        }
        else if (fromUnit.equals("BTU"))
        {
            valueInJoules = value * 1055.06;
        }

        double result = 0;
        if (toUnit.equals("Joule"))
        {
            result = valueInJoules;
        }
        else if (toUnit.equals("Kilojoule"))
        {
            result = valueInJoules / 1000;
        }
        else if (toUnit.equals("Calorie"))
        {
            result = valueInJoules / 4.184;
        }
        else if (toUnit.equals("Kilocalorie"))
        {
            result = valueInJoules / 4184;
        }
        else if (toUnit.equals("Kilowatt-hour"))
        {
            result = valueInJoules / 3.6e6;
        }
        else if (toUnit.equals("BTU"))
        {
            result = valueInJoules / 1055.06;
        }

        return result;
    }
}