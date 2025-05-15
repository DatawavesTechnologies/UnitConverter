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

import java.util.HashMap;
import java.util.Map;

public class ShoeActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textResult;

    private String[] shoeSystems = {"US", "UK", "EU", "India", "Japan", "Centimeters"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoe_activity);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, shoeSystems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString();
                if (inputStr.isEmpty()) {
                    Toast.makeText(ShoeActivity.this, "Please enter a shoe size", Toast.LENGTH_SHORT).show();
                    return;
                }

                int value;
                try {
                    value = Integer.parseInt(inputStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(ShoeActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fromSystem = spinnerFrom.getSelectedItem().toString();
                String toSystem = spinnerTo.getSelectedItem().toString();

                int result = convertShoeSize(value, fromSystem, toSystem);
                textResult.setText(result + " (" + toSystem + ")");
            }
        });
    }

    private int convertShoeSize(int value, String fromSystem, String toSystem) {
        Map<String, Integer> offsetMap = new HashMap<>();
        offsetMap.put("US", 0);
        offsetMap.put("UK", -1);
        offsetMap.put("EU", 33);
        offsetMap.put("India", -1);
        offsetMap.put("Japan", 18);
        offsetMap.put("Centimeters", 18);

        int fromOffset = offsetMap.containsKey(fromSystem) ? offsetMap.get(fromSystem) : 0;
        int toOffset = offsetMap.containsKey(toSystem) ? offsetMap.get(toSystem) : 0;

        int usSize = value + fromOffset;
        return usSize - toOffset;
    }
}

