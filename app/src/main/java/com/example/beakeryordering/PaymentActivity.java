package com.example.beakeryordering;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    private EditText etCardNumber, etCardName, etExpiryDate, etCVV;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardName = findViewById(R.id.etCardName);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCVV = findViewById(R.id.etCVV);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Handle Submit button click
        btnSubmit.setOnClickListener(v -> {
            String cardNumber = etCardNumber.getText().toString().trim();
            String cardName = etCardName.getText().toString().trim();
            String expiryDate = etExpiryDate.getText().toString().trim();
            String cvv = etCVV.getText().toString().trim();

            if (cardNumber.isEmpty() || cardName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simulate a successful payment
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        });
    }
}
