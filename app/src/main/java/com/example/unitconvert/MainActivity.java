package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private LinearLayout convertLayout;

    private Spinner spinnerFrom, spinnerTo;
    private EditText inputValue, etAmountTo;
    private Button buttonConvert;

    private String unitType = "length"; // default value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        convertLayout = findViewById(R.id.convertLayout);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputValue = findViewById(R.id.inputValue);
        etAmountTo = findViewById(R.id.etAmountTo);
        buttonConvert = findViewById(R.id.buttonConvert);

        inputValue.setShowSoftInputOnFocus(false); // Disable default keyboard

        // Set click listeners for each category
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof LinearLayout) {
                final LinearLayout categoryLayout = (LinearLayout) child;

                // Get the label from the TextView
                TextView tvCategory = null;
                for (int j = 0; j < categoryLayout.getChildCount(); j++) {
                    View v = categoryLayout.getChildAt(j);
                    if (v instanceof TextView) {
                        tvCategory = (TextView) v;
                        break;
                    }
                }

                final String categoryName = (tvCategory != null) ? tvCategory.getText().toString().toLowerCase() : "length";

                categoryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unitType = categoryName;

                        // Switch UI
                        gridLayout.setVisibility(View.GONE);
                        convertLayout.setVisibility(View.VISIBLE);

                        // Load appropriate units
                        setupSpinners();

                        // Set Convert button logic
                        buttonConvert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                performConversion();
                            }
                        });
                    }
                });
            }
        }
        ImageButton buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();  // Close activity if needed
            }
        });

        setUpCustomKeypad();
    }
    @Override
    public void onBackPressed() {
        if (convertLayout.getVisibility() == View.VISIBLE) {
            convertLayout.setVisibility(View.GONE);
            gridLayout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed(); // Default behavior (exit)
        }
    }

    private void setupSpinners() {
        String[] units;
        switch (unitType) {
            case "angle":
                units = new String[]{"Degree", "Radian", "Gradian", "Minutes", "Seconds"};
                break;
            case "length":
                units = new String[]{"Millimeter", "Centimeter", "Meter", "Kilometer", "Inch", "Foot", "Yard", "Mile"};
                break;
            case "weight":
                units = new String[]{"Milligram", "Gram", "Kilogram", "Ton", "Pound", "Ounce"};
                break;
            case "temperature":
                units = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
                break;
            case "area":
                units = new String[]{"Square meter", "Square kilometer", "Acre", "Hectare", "Square foot", "Square yard"};
                break;
            case "volume":
                units = new String[]{"Milliliter", "Liter", "Cubic meter", "Gallon", "Quart", "Pint", "Cubic inch"};
                break;
            case "speed":
                units = new String[]{"Meters per second", "Kilometers per hour", "Miles per hour", "Feet per second", "Knots"};
                break;
            case "time":
                units = new String[]{"Milliseconds", "Seconds", "Minutes", "Hours", "Days", "Weeks", "Months", "Years"};
                break;
            case "pressure":
                units = new String[]{"Pascal", "Bar", "PSI", "Atmosphere", "Torr", "Millimeters of mercury"};
                break;
            case "power":
                units = new String[]{"Watt", "Kilowatt", "Horsepower", "Megawatt"};
                break;
            case "energy":
                units = new String[]{"Joule", "Kilojoule", "Calorie", "Kilocalorie", "Kilowatt-hour", "BTU"};
                break;
            case "mileage":
                units = new String[]{"Kilometers per liter", "Liters per 100 km"};
                break;
            case "data":
                units = new String[]{"Bit", "Byte", "Kilobyte", "Megabyte", "Gigabyte", "Terabyte"};
                break;
            case "currency":
                units = new String[]{"USD", "EUR", "INR", "GBP", "JPY", "CAD", "AUD"};
                break;
            case "frequency":
                units = new String[]{"Hertz", "Kilohertz", "Megahertz", "Gigahertz"};
                break;
            case "cooking":
                units = new String[]{"Teaspoon", "Tablespoon", "Cup", "Ounce", "Milliliter", "Liter", "Gram", "Pound"};
                break;
            case "image":
                units = new String[]{"DPI", "PPI"};
                break;
            case "shoe":
                units = new String[]{"US", "UK", "EU", "India", "Japan", "Centimeters"};
                break;
            case "clothing":
                units = new String[]{"US", "UK", "EU", "Asia", "S", "M", "L", "XL"};
                break;
            case "screen":
                units = new String[]{"Pixels", "DP", "Inches", "DPI"};
                break;
            default:
                units = new String[]{"Unit1", "Unit2"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    private void performConversion() {
        String from = spinnerFrom.getSelectedItem().toString();
        String to = spinnerTo.getSelectedItem().toString();
        String inputStr = inputValue.getText().toString();

        if (!inputStr.isEmpty()) {
            double input = Double.parseDouble(inputStr);
            double result = convertDynamic(unitType, input, from, to);
            etAmountTo.setText(String.valueOf(result));
        } else {
            etAmountTo.setText("Enter value");
        }
    }
    private double convertDynamic(String type, double value, String from, String to) {
        switch (type) {
            case "angle": return convertAngle(value, from, to);
            case "length": return convertGeneric(value, from, to, getLengthMap());
            case "weight": return convertGeneric(value, from, to, getWeightMap());
            case "temperature": return convertTemperature(value, from, to);
            case "area": return convertGeneric(value, from, to, getAreaMap());
            case "volume": return convertGeneric(value, from, to, getVolumeMap());
            case "speed": return convertGeneric(value, from, to, getSpeedMap());
            case "time": return convertGeneric(value, from, to, getTimeMap());
            case "pressure": return convertGeneric(value, from, to, getPressureMap());
            case "power": return convertGeneric(value, from, to, getPowerMap());
            case "energy": return convertGeneric(value, from, to, getEnergyMap());
            case "mileage": return convertFuel(value, from, to);
            case "data": return convertGeneric(value, from, to, getDataMap());
            case "currency": return convertCurrency(value, from, to);
            case "frequency": return convertGeneric(value, from, to, getFrequencyMap());
            case "cooking": return convertGeneric(value, from, to, getCookingMap());
            case "image": return convertDigitalImage(value, from, to);
            case "shoe": return convertShoeSize(value, from, to);
            case "clothing": return value;
            case "screen": return convertScreenUnit(value, from, to);
            default: return 0;
        }
    }

    private double convertGeneric(double value, String from, String to, HashMap<String, Double> map) {
        return (value * map.get(from)) / map.get(to);
    }

    private HashMap<String, Double> getLengthMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Millimeter", 0.001);
        map.put("Centimeter", 0.01);
        map.put("Meter", 1.0);
        map.put("Kilometer", 1000.0);
        map.put("Inch", 0.0254);
        map.put("Foot", 0.3048);
        map.put("Yard", 0.9144);
        map.put("Mile", 1609.34);
        return map;
    }

    private HashMap<String, Double> getWeightMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Milligram", 0.000001);
        map.put("Gram", 0.001);
        map.put("Kilogram", 1.0);
        map.put("Ton", 1000.0);
        map.put("Pound", 0.453592);
        map.put("Ounce", 0.0283495);
        return map;
    }

    private HashMap<String, Double> getAreaMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Square meter", 1.0);
        map.put("Square kilometer", 1e6);
        map.put("Acre", 4046.86);
        map.put("Hectare", 10000.0);
        map.put("Square foot", 0.092903);
        map.put("Square yard", 0.836127);
        return map;
    }

    private HashMap<String, Double> getVolumeMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Milliliter", 0.001);
        map.put("Liter", 1.0);
        map.put("Cubic meter", 1000.0);
        map.put("Gallon", 3.78541);
        map.put("Quart", 0.946353);
        map.put("Pint", 0.473176);
        map.put("Cubic inch", 0.0163871);
        return map;
    }

    private HashMap<String, Double> getSpeedMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Meters per second", 1.0);
        map.put("Kilometers per hour", 0.277778);
        map.put("Miles per hour", 0.44704);
        map.put("Feet per second", 0.3048);
        map.put("Knots", 0.514444);
        return map;
    }

    private HashMap<String, Double> getTimeMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Milliseconds", 0.001);
        map.put("Seconds", 1.0);
        map.put("Minutes", 60.0);
        map.put("Hours", 3600.0);
        map.put("Days", 86400.0);
        map.put("Weeks", 604800.0);
        map.put("Months", 2.628e6);
        map.put("Years", 3.154e7);
        return map;
    }

    private HashMap<String, Double> getPressureMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Pascal", 1.0);
        map.put("Bar", 100000.0);
        map.put("PSI", 6894.76);
        map.put("Atmosphere", 101325.0);
        map.put("Torr", 133.322);
        map.put("Millimeters of mercury", 133.322);
        return map;
    }

    private HashMap<String, Double> getPowerMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Watt", 1.0);
        map.put("Kilowatt", 1000.0);
        map.put("Horsepower", 745.7);
        map.put("Megawatt", 1e6);
        return map;
    }

    private HashMap<String, Double> getEnergyMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Joule", 1.0);
        map.put("Kilojoule", 1000.0);
        map.put("Calorie", 4.184);
        map.put("Kilocalorie", 4184.0);
        map.put("Kilowatt-hour", 3.6e6);
        map.put("BTU", 1055.06);
        return map;
    }

    private HashMap<String, Double> getDataMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Bit", 0.125);
        map.put("Byte", 1.0);
        map.put("Kilobyte", 1024.0);
        map.put("Megabyte", 1048576.0);
        map.put("Gigabyte", 1.074e9);
        map.put("Terabyte", 1.1e12);
        return map;
    }

    private HashMap<String, Double> getFrequencyMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Hertz", 1.0);
        map.put("Kilohertz", 1e3);
        map.put("Megahertz", 1e6);
        map.put("Gigahertz", 1e9);
        return map;
    }

    private HashMap<String, Double> getCookingMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("Teaspoon", 4.92892);
        map.put("Tablespoon", 14.7868);
        map.put("Cup", 240.0);
        map.put("Ounce", 29.5735);
        map.put("Milliliter", 1.0);
        map.put("Liter", 1000.0);
        map.put("Gram", 1.0); // Approximation
        map.put("Pound", 453.592);
        return map;
    }

    private double convertFuel(double value, String from, String to) {
        if (from.equals("Liters per 100 km") && to.equals("Kilometers per liter"))
            return 100 / value;
        if (from.equals("Kilometers per liter") && to.equals("Liters per 100 km"))
            return 100 / value;
        return value;
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value;
        double celsius;
        if (from.equals("Celsius")) celsius = value;
        else if (from.equals("Fahrenheit")) celsius = (value - 32) * 5 / 9;
        else celsius = value - 273.15;

        if (to.equals("Celsius")) return celsius;
        else if (to.equals("Fahrenheit")) return (celsius * 9 / 5) + 32;
        else return celsius + 273.15;
    }

    private double convertCurrency(double value, String fromCurrency, String toCurrency) {
        int fromIndex = -1, toIndex = -1;
        final double[] exchangeRatesToUsd = {
                1.0, 0.93, 0.82, 76.0, 110.0, 1.50, 1.35, 0.91, 6.5, 20.0, 5.5, 0.013,
                14.0, 1100.0, 1.3, 3.67, 4.2, 4.5, 33.0, 23.0, 50.0
        };

        final String[] currencies = {
                "USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY", "MXN", "BRL", "RUB",
                "ZAR", "KRW", "SGD", "AED", "MYR", "THB", "VND", "PHP"
        };
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].equals(fromCurrency)) {
                fromIndex = i;
            }
            if (currencies[i].equals(toCurrency)) {
                toIndex = i;
            }
        }

        if (fromIndex == -1 || toIndex == -1) {
            return 0;
        }

        double amountInUsd = value / exchangeRatesToUsd[fromIndex];

        return amountInUsd * exchangeRatesToUsd[toIndex];
    }

    private double convertDigitalImage(double value, String from, String to) {
        from = from.trim().toUpperCase();
        to = to.trim().toUpperCase();

        switch (from) {
            case "DPI":
            case "PPI":
                break;
            default:
                return 0;
        }

        switch (to) {
            case "DPI":
            case "PPI":
                return value;
            default:
                return 0;
        }
    }
    private double convertScreenUnit(double value, String from, String to) {
        final double DPI = 160.0;

        double px;
        switch (from) {
            case "Pixels":
                px = value;
                break;
            case "DP":
                px = value * (DPI / 160.0);
                break;
            case "Inches":
                px = value * DPI;
                break;
            default:
                return 0;
        }

        switch (to) {
            case "Pixels":
                return px;
            case "DP":
                return px / (DPI / 160.0);
            case "Inches":
                return px / DPI;
            default:
                return 0;
        }
    }

    private double convertShoeSize(double value, String from, String to) {
        double cm;

        switch (from) {
            case "US":
                cm = value * 0.847 + 18.0; break; // approx
            case "UK":
                cm = value * 0.847 + 18.5; break;
            case "EU":
                cm = value * 0.667 + 11.5; break;
            case "India":
                cm = value * 0.847 + 18.5; break;
            case "Japan":
                cm = value; break; // Japan sizes are in cm
            case "Centimeters (cm)":
                cm = value; break;
            default:
                return 0;
        }

        switch (to) {
            case "US":
                return (cm - 18.0) / 0.847;
            case "UK":
                return (cm - 18.5) / 0.847;
            case "EU":
                return (cm - 11.5) / 0.667;
            case "India":
                return (cm - 18.5) / 0.847;
            case "Japan":
                return cm;
            case "Centimeters (cm)":
                return cm;
            default:
                return 0;
        }
    }



    private double convertAngle(double value, String from, String to) {
        double deg;
        switch (from) {
            case "Degree": deg = value; break;
            case "Radian": deg = Math.toDegrees(value); break;
            case "Gradian": deg = value * 0.9; break;
            case "Minutes": deg = value / 60.0; break;
            case "Seconds": deg = value / 3600.0; break;
            default: return 0;
        }

        switch (to) {
            case "Degree": return deg;
            case "Radian": return Math.toRadians(deg);
            case "Gradian": return deg / 0.9;
            case "Minutes": return deg * 60.0;
            case "Seconds": return deg * 3600.0;
            default: return 0;
        }
    }

    private void setUpCustomKeypad() {
        int[] buttonIds = new int[] {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDelete,R.id.buttonDot
        };

        View.OnClickListener keyListener = v -> {
            Button btn = (Button) v;
            String btnText = btn.getText().toString();
            String current = inputValue.getText().toString();

            if (btnText.equals("⌫")) {
                if (!current.isEmpty()) {
                    inputValue.setText(current.substring(0, current.length() - 1));
                    inputValue.setSelection(inputValue.getText().length());
                }
            } else {
                inputValue.setText(current + btnText);
                inputValue.setSelection(inputValue.getText().length());
            }
        };

        for (int id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener(keyListener);
        }
    }
}
