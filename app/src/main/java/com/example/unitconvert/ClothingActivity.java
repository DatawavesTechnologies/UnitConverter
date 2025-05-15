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

import java.util.Map;

public class ClothingActivity extends AppCompatActivity {

    private Spinner spinnerFromRegion, spinnerToRegion, spinnerSize;
    private Button buttonConvert;
    private TextView textResult;

    private String[] regions = {"US", "UK", "EU", "Asia"};
    private String[] standardSizes = {"S", "M", "L", "XL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing_activity);

        spinnerFromRegion = findViewById(R.id.spinnerFromRegion);
        spinnerToRegion = findViewById(R.id.spinnerToRegion);
        spinnerSize = findViewById(R.id.spinnerSize);
        buttonConvert = findViewById(R.id.buttonConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, regions);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, standardSizes);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromRegion.setAdapter(regionAdapter);
        spinnerToRegion.setAdapter(regionAdapter);
        spinnerSize.setAdapter(sizeAdapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = spinnerSize.getSelectedItem().toString();
                String fromRegion = spinnerFromRegion.getSelectedItem().toString();
                String toRegion = spinnerToRegion.getSelectedItem().toString();

                String convertedSize = convertClothingSize(size, fromRegion, toRegion);
                textResult.setText(String.format("%s (%s)", convertedSize, toRegion));
            }
        });
    }

    private String convertClothingSize(String size, String fromRegion, String toRegion) {
        Map<String, Map<String, String>> regionToStandardMap = Map.of(
                "US", Map.of("S", "S", "M", "M", "L", "L", "XL", "XL"),
                "UK", Map.of("8", "S", "10", "M", "12", "L", "14", "XL"),
                "EU", Map.of("36", "S", "38", "M", "40", "L", "42", "XL"),
                "Asia", Map.of("85", "S", "90", "M", "95", "L", "100", "XL")
        );

        Map<String, Map<String, String>> standardToRegionMap = Map.of(
                "US", Map.of("S", "S", "M", "M", "L", "L", "XL", "XL"),
                "UK", Map.of("S", "8", "M", "10", "L", "12", "XL", "14"),
                "EU", Map.of("S", "36", "M", "38", "L", "40", "XL", "42"),
                "Asia", Map.of("S", "85", "M", "90", "L", "95", "XL", "100")
        );

        // Convert selected size to standard size
        String standardSize = regionToStandardMap.getOrDefault(fromRegion, Map.of()).get(size);
        if (standardSize == null) {
            return "Invalid size or region";
        }

        // Convert from standard size to target region size
        return standardToRegionMap.getOrDefault(toRegion, Map.of()).getOrDefault(standardSize, size);
    }
}
