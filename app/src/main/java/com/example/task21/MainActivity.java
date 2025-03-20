package com.example.task21;

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

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner, destUnitSpinner;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destUnitSpinner = findViewById(R.id.destUnitSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Define available unit options
        String[] units = {"Celsius", "Fahrenheit", "Kelvin", "Inch", "Foot", "Yard", "Mile", "Pound", "Ounce", "Ton"};

        // Set up Spinners with unit options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        sourceUnitSpinner.setAdapter(adapter);
        destUnitSpinner.setAdapter(adapter);

        // Handle Convert Button Click
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }



    private void convertUnits() {
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destUnit = destUnitSpinner.getSelectedItem().toString();
        String inputString = inputValue.getText().toString();

        if (inputString.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(inputString);
        String conversionDetails = getConversionDetails(value, sourceUnit, destUnit);
        resultText.setText(conversionDetails);
    }

    private String getConversionDetails(double value, String sourceUnit, String destUnit) {
        double cmValue = 0, kgValue = 0;
        boolean isLength = false, isWeight = false;
        String intermediateResult = "";

        // Length Conversions (Convert to cm First)
        switch (sourceUnit) {
            case "Inch": cmValue = value * 2.54; isLength = true; break;
            case "Foot": cmValue = value * 30.48; isLength = true; break;
            case "Yard": cmValue = value * 91.44; isLength = true; break;
            case "Mile": cmValue = value * 160934; isLength = true; break;
            case "Centimeter": cmValue = value; isLength = true; break;
            case "Kilometer": cmValue = value * 100000; isLength = true; break;
        }

        // Convert from cm to the target unit
        double finalValue = value;
        if (isLength) {
            intermediateResult = String.format("Intermediate Value: %.2f cm\n", cmValue);
            switch (destUnit) {
                case "Inch": finalValue = cmValue / 2.54; break;
                case "Foot": finalValue = cmValue / 30.48; break;
                case "Yard": finalValue = cmValue / 91.44; break;
                case "Mile": finalValue = cmValue / 160934; break;
                case "Centimeter": finalValue = cmValue; break;
                case "Kilometer": finalValue = cmValue / 100000; break;
            }
        }

        // Weight Conversions (Convert to kg First)
        switch (sourceUnit) {
            case "Pound": kgValue = value * 0.453592; isWeight = true; break;
            case "Ounce": kgValue = value * 0.0283495; isWeight = true; break;
            case "Ton": kgValue = value * 907.185; isWeight = true; break;
            case "Gram": kgValue = value / 1000; isWeight = true; break;
            case "Kilogram": kgValue = value; isWeight = true; break;
        }

        // Convert from kg to the target unit
        if (isWeight) {
            intermediateResult = String.format("Intermediate Value: %.2f g\n", kgValue * 1000);
            switch (destUnit) {
                case "Pound": finalValue = kgValue / 0.453592; break;
                case "Ounce": finalValue = kgValue / 0.0283495; break;
                case "Ton": finalValue = kgValue / 907.185; break;
                case "Gram": finalValue = kgValue * 1000; break;
                case "Kilogram": finalValue = kgValue; break;
            }
        }

        // Temperature Conversions
        if (sourceUnit.equals("Celsius") && destUnit.equals("Fahrenheit")) {
            finalValue = (value * 1.8) + 32;
        } else if (sourceUnit.equals("Fahrenheit") && destUnit.equals("Celsius")) {
            finalValue = (value - 32) / 1.8;
        } else if (sourceUnit.equals("Celsius") && destUnit.equals("Kelvin")) {
            finalValue = value + 273.15;
        } else if (sourceUnit.equals("Kelvin") && destUnit.equals("Celsius")) {
            finalValue = value - 273.15;
        }

        // Display final result
        return intermediateResult + String.format("Final Result: %.2f %s", finalValue, destUnit);
    }

}


