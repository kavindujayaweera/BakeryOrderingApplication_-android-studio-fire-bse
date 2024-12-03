package com.example.beakeryordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Product_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
    }

    // This method is triggered when an order button is clicked
    public void onOrderButtonClick(View view) {
        // Use if-else to check the button ID
        if (view.getId() == R.id.btnOrder1 || view.getId() == R.id.btnOrder2 ||
                view.getId() == R.id.btnOrder3 || view.getId() == R.id.btnOrder4) {
            // Start the PaymentActivity when any order button is clicked
            Intent intent = new Intent(Product_list.this, PaymentActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid Button", Toast.LENGTH_SHORT).show();
        }
    }}
