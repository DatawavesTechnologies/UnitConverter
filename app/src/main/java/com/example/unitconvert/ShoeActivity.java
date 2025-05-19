package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

//public class ShoeActivity extends AppCompatActivity {
//
//    private EditText inputValue, textResult;
//    private Spinner spinnerFrom, spinnerTo;
//    private Button buttonConvert;
//
//    private final String[] shoeSystems = {"US", "UK", "EU", "India", "Japan", "Centimeters"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.shoe_activity);
//
//        inputValue = findViewById(R.id.inputValue);
//        spinnerFrom = findViewById(R.id.spinnerFrom);
//        spinnerTo = findViewById(R.id.spinnerTo);
//        buttonConvert = findViewById(R.id.buttonConvert);
//        textResult = findViewById(R.id.textResult);
//        inputValue.setShowSoftInputOnFocus(false); // Disable default system keyboard
//
//        // Set up the adapter for the shoe size systems
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shoeSystems);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinnerFrom.setAdapter(adapter);
//        spinnerTo.setAdapter(adapter);
//
//        // Handle button click to trigger the conversion
//        buttonConvert.setOnClickListener(v -> {
//            String inputStr = inputValue.getText().toString();
//            if (inputStr.isEmpty()) {
//                Toast.makeText(ShoeActivity.this, "Please enter a shoe size", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Parse input to integer
//            int value;
//            try {
//                value = Integer.parseInt(inputStr);
//            } catch (NumberFormatException e) {
//                Toast.makeText(ShoeActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String fromSystem = spinnerFrom.getSelectedItem().toString();
//            String toSystem = spinnerTo.getSelectedItem().toString();
//
//            // Convert the shoe size
//            int result = convertShoeSize(value, fromSystem, toSystem);
//            textResult.setText(String.format("%d (%s)", result, toSystem));
//        });
//
//        setUpCustomKeypad(); // Set up custom input keypad
//    }
//
//    private void setUpCustomKeypad() {
//        int[] buttonIds = new int[]{
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
//    private int convertShoeSize(int value, String fromSystem, String toSystem) {
//        // Define conversion offsets for each shoe system
//        Map<String, Integer> offsetMap = new HashMap<>();
//        offsetMap.put("US", 0);
//        offsetMap.put("UK", -1);
//        offsetMap.put("EU", 33);
//        offsetMap.put("India", -1);
//        offsetMap.put("Japan", 18);
//        offsetMap.put("Centimeters", 18);
//
//        // Get the offset for the input shoe system and target system
//        int fromOffset = offsetMap.getOrDefault(fromSystem, 0);
//        int toOffset = offsetMap.getOrDefault(toSystem, 0);
//
//        // Convert the input value to the US shoe size
//        int usSize = value + fromOffset;
//
//        // Convert from US size to the target shoe system
//        return usSize - toOffset;
//    }
//}
