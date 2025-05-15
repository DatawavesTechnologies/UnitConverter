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

public class CookingActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] cookingUnits = {
            "Teaspoon", "Tablespoon", "Cup", "Ounce (fl oz)",
            "Milliliter", "Liter", "Gram", "Pound"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooking_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cookingUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(CookingActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();

                double result = convertCooking(value, fromUnit, toUnit);
                textResult.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertCooking(double value, String fromUnit, String toUnit) {
        double valueInMl = 0;

        if (fromUnit.equals("Teaspoon"))
        {
            valueInMl = value * 4.92892;
        }
        else if (fromUnit.equals("Tablespoon"))
        {
            valueInMl = value * 14.7868;
        }
        else if (fromUnit.equals("Cup"))
        {
            valueInMl = value * 240;
        }
        else if (fromUnit.equals("Ounce (fl oz)"))
        {
            valueInMl = value * 29.5735;
        }
        else if (fromUnit.equals("Milliliter"))
        {
            valueInMl = value;
        }
        else if (fromUnit.equals("Liter"))
        {
            valueInMl = value * 1000;
        }
        else if (fromUnit.equals("Gram"))
        {
            valueInMl = value;
        }
        else if (fromUnit.equals("Pound"))
        {
            valueInMl = value * 453.592;
        }
        double result = 0;
        if (toUnit.equals("Teaspoon")) {
            result = valueInMl / 4.92892;
        }
        else if (toUnit.equals("Tablespoon"))
        {
            result = valueInMl / 14.7868;
        }
        else if (toUnit.equals("Cup"))
        {
            result = valueInMl / 240;
        }
        else if (toUnit.equals("Ounce (fl oz)"))
        {
            result = valueInMl / 29.5735;
        }
        else if (toUnit.equals("Milliliter"))
        {
            result = valueInMl;
        }
        else if (toUnit.equals("Liter"))
        {
            result = valueInMl / 1000;
        }
        else if (toUnit.equals("Gram"))
        {
            result = valueInMl;
        }
        else if (toUnit.equals("Pound"))
        {
            result = valueInMl / 453.592;
        }

        return result;
    }
}
