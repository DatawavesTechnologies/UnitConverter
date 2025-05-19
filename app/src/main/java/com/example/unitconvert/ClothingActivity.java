package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

//public class ClothingActivity extends AppCompatActivity {}
//
//    private EditText inputValue, resultText;
//    private Spinner spinnerFrom, spinnerTo;
//    private Button convertButton;
//
//    private final String[] clothingSystems = {
//            "US", "UK", "EU", "Asia", "Small (S)", "Medium (M)", "Large (L)", "Extra Large (XL)"
//    };
//
//    private final Map<String, Integer> sizeScaleMap = new HashMap<String, Integer>() {{
//        put("US", 1);
//        put("UK", 1);
//        put("EU", 2);
//        put("Asia", 0);
//        put("Small (S)", 38);
//        put("Medium (M)", 40);
//        put("Large (L)", 42);
//        put("Extra Large (XL)", 44);
//    }};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.clothing_activity);
//
//        inputValue = findViewById(R.id.inputValue);
//        resultText = findViewById(R.id.resultText);
//        spinnerFrom = findViewById(R.id.spinnerFrom);
//        spinnerTo = findViewById(R.id.spinnerTo);
//        convertButton = findViewById(R.id.buttonConvert);
//
//        inputValue.setShowSoftInputOnFocus(false); // Disable default keyboard
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, clothingSystems);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerFrom.setAdapter(adapter);
//        spinnerTo.setAdapter(adapter);
//
//        convertButton.setOnClickListener(v -> convertClothingSize());
//
//        setUpCustomKeypad(); // 🔑 Add this to enable custom input
//    }
//
//    private void setUpCustomKeypad() {
//        int[] buttonIds = new int[] {
//                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
//                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
//                R.id.button8, R.id.button9, R.id.buttonDelete
//        };
//
//        View.OnClickListener keyListener = v -> {
//            Button btn = (Button) v;
//            String btnText = btn.getText().toString();
//            String current = inputValue.getText().toString();
//
//            if (btnText.equals("⌫")) {
//                if (!current.isEmpty()) {
//                    inputValue.setText(current.substring(0, current.length() - 1));
//                    inputValue.setSelection(inputValue.getText().length());
//                }
//            } else {
//                inputValue.setText(current + btnText);
//                inputValue.setSelection(inputValue.getText().length());
//            }
//        };
//
//        for (int id : buttonIds) {
//            Button b = findViewById(id);
//            b.setOnClickListener(keyListener);
//        }
//    }
//
//    private void convertClothingSize() {
//        String inputStr = inputValue.getText().toString().trim();
//        if (inputStr.isEmpty()) {
//            Toast.makeText(this, "Please enter a size value", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String fromUnit = spinnerFrom.getSelectedItem().toString();
//        String toUnit = spinnerTo.getSelectedItem().toString();
//
//        try {
//            int standardizedSize = getStandardSize(inputStr, fromUnit);
//            String convertedSize = getSizeFromStandard(standardizedSize, toUnit);
//            resultText.setText(String.format("%s (%s)", convertedSize, toUnit));
//        } catch (Exception e) {
//            resultText.setText("Conversion not possible.");
//        }
//    }
//
//    private int getStandardSize(String input, String unit) throws Exception {
//        switch (unit) {
//            case "US":
//            case "UK":
//            case "EU":
//            case "Asia":
//                return Integer.parseInt(input); // Numeric sizes
//            case "Small (S)":
//                return 38;
//            case "Medium (M)":
//                return 40;
//            case "Large (L)":
//                return 42;
//            case "Extra Large (XL)":
//                return 44;
//            default:
//                throw new Exception("Unknown unit");
//        }
//    }
//
//    private String getSizeFromStandard(int size, String unit) {
//        switch (unit) {
//            case "US":
//            case "UK":
//            case "EU":
//            case "Asia":
//                return String.valueOf(size);
//            case "Small (S)":
//                return size <= 38 ? "S" : "Too big for S";
//            case "Medium (M)":
//                return size <= 40 ? "M" : "Too big for M";
//            case "Large (L)":
//                return size <= 42 ? "L" : "Too big for L";
//            case "Extra Large (XL)":
//                return size >= 43 ? "XL" : "Too small for XL";
//            default:
//                return "N/A";
//        }
//    }

