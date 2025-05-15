package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class VolumeActivity extends AppCompatActivity {

    String[] volumeUnits = {
            "Milliliter", "Liter", "Cubic meter", "Gallon (US)",
            "Quart", "Pint", "Cubic inch"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_activity);

        final EditText inputVolume = findViewById(R.id.inputVolume);
        final Spinner fromSpinner = findViewById(R.id.fromVolumeUnit);
        final Spinner toSpinner = findViewById(R.id.toVolumeUnit);
        final Button convertBtn = findViewById(R.id.convertVolumeBtn);
        final TextView resultView = findViewById(R.id.volumeResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, volumeUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputVolume.getText().toString().trim();
                if (input.isEmpty()) {
                    resultView.setText("Enter a value");
                    return;
                }

                double value = Double.parseDouble(input);
                String fromUnit = fromSpinner.getSelectedItem().toString();
                String toUnit = toSpinner.getSelectedItem().toString();

                double result = convertVolume(value, fromUnit, toUnit);
                resultView.setText(String.format("%.4f %s", result, toUnit));
            }
        });
    }

    private double convertVolume(double value, String fromUnit, String toUnit) {
        double valueInLiters = 0;

        if (fromUnit.equals("Milliliter"))
        {
            valueInLiters = value / 1000;
        }
        else if (fromUnit.equals("Liter"))
        {
            valueInLiters = value;
        }
        else if (fromUnit.equals("Cubic meter"))
        {
            valueInLiters = value * 1000;
        }
        else if (fromUnit.equals("Gallon (US)"))
        {
            valueInLiters = value * 3.78541;
        }
        else if (fromUnit.equals("Quart"))
        {
            valueInLiters = value * 0.946353;
        }
        else if (fromUnit.equals("Pint"))
        {
            valueInLiters = value * 0.473176;
        }
        else if (fromUnit.equals("Cubic inch"))
        {
            valueInLiters = value * 0.0163871;
        }

        double result = 0;
        if (toUnit.equals("Milliliter"))
        {
            result = valueInLiters * 1000;
        }
        else if (toUnit.equals("Liter"))
        {
            result = valueInLiters;
        }
        else if (toUnit.equals("Cubic meter"))
        {
            result = valueInLiters / 1000;
        }
        else if (toUnit.equals("Gallon (US)"))
        {
            result = valueInLiters / 3.78541;
        }
        else if (toUnit.equals("Quart"))
        {
            result = valueInLiters / 0.946353;
        }
        else if (toUnit.equals("Pint"))
        {
            result = valueInLiters / 0.473176;
        }
        else if (toUnit.equals("Cubic inch"))
        {
            result = valueInLiters / 0.0163871;
        }

        return result;
    }
}
