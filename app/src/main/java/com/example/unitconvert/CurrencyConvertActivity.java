package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CurrencyConvertActivity extends AppCompatActivity {

    private EditText etAmountFrom, etAmountTo;
    private Spinner spinnerFrom, spinnerTo;
    private Button convertButton;

    // Define the currencies and their exchange rates to USD
    private final String[] currencies = {
            "USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY", "MXN", "BRL", "RUB",
            "ZAR", "KRW", "SGD", "AED", "MYR", "THB", "VND", "PHP"
    };

    private final double[] exchangeRatesToUsd = {
            1.0, 0.93, 0.82, 76.0, 110.0, 1.50, 1.35, 0.91, 6.5, 20.0, 5.5, 0.013,
            14.0, 1100.0, 1.3, 3.67, 4.2, 4.5, 33.0, 23.0, 50.0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_activity); // Ensure this matches the correct XML file

        // Initialize views
        etAmountFrom = findViewById(R.id.etAmountFrom);
        etAmountTo = findViewById(R.id.etAmountTo);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convert_button);

        // Set up the currency spinner with 20 currencies
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Disable soft input on amount field to prevent keyboard popping
        etAmountFrom.setShowSoftInputOnFocus(false);

        // Set the click listener for the convert button
        convertButton.setOnClickListener(v -> {
            String inputStr = etAmountFrom.getText().toString();
            if (inputStr.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double inputValue = Double.parseDouble(inputStr);
                String fromCurrency = spinnerFrom.getSelectedItem().toString();
                String toCurrency = spinnerTo.getSelectedItem().toString();

                double result = convertCurrency(inputValue, fromCurrency, toCurrency);
                etAmountTo.setText(String.format("%.2f", result));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the custom keyboard functionality
        setupKeyboard();
    }

    // Convert currency from one unit to another using exchange rates
    private double convertCurrency(double value, String fromCurrency, String toCurrency) {
        int fromIndex = -1, toIndex = -1;

        // Find the indexes of the from and to currencies
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].equals(fromCurrency)) {
                fromIndex = i;
            }
            if (currencies[i].equals(toCurrency)) {
                toIndex = i;
            }
        }

        if (fromIndex == -1 || toIndex == -1) {
            return 0; // Invalid currencies
        }

        // Convert the amount to USD first
        double amountInUsd = value / exchangeRatesToUsd[fromIndex];

        // Convert USD to the target currency
        return amountInUsd * exchangeRatesToUsd[toIndex];
    }

    // Set up custom keyboard interaction
    private void setupKeyboard() {
        GridLayout keypad = findViewById(R.id.keypad);

        for (int i = 0; i < keypad.getChildCount(); i++) {
            Button keyButton = (Button) keypad.getChildAt(i);
            keyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = ((Button) v).getText().toString();
                    handleKeyPress(buttonText);
                }
            });
        }
    }

    // Handle key press from the custom keyboard
    private void handleKeyPress(String buttonText) {
        String currentText = etAmountFrom.getText().toString();

        switch (buttonText) {
            case "⌫":  // Backspace functionality
                if (currentText.length() > 0) {
                    currentText = currentText.substring(0, currentText.length() - 1);
                    etAmountFrom.setText(currentText);
                }
                break;

            case ".":
                // Prevent multiple decimal points
                if (!currentText.contains(".")) {
                    currentText += buttonText;
                    etAmountFrom.setText(currentText);
                }
                break;

            default:
                // Append the pressed number or symbol
                currentText += buttonText;
                etAmountFrom.setText(currentText);
                break;
        }
    }
}
