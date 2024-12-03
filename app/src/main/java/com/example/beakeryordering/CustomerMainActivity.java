package com.example.beakeryordering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CustomerMainActivity extends AppCompatActivity {

    private Button btnViewProducts, btnCheckAvailability, btnBuyProducts, btnTrackOrder, btnSubmitFeedback, btnLogout;
    private TextView txtWelcome;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        // Initialize Firestore database
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnCheckAvailability = findViewById(R.id.btnCheckAvailability);
        btnBuyProducts = findViewById(R.id.btnBuyProducts);
        btnTrackOrder = findViewById(R.id.btnTrackOrder);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        btnLogout = findViewById(R.id.btnLogout);
        txtWelcome = findViewById(R.id.txtWelcome);

        // Set welcome text (Handle null safety for intent extras)
        String userName = getIntent().getStringExtra("USER_NAME");
        txtWelcome.setText(userName != null ? "Welcome " + userName + "!" : "Welcome, User!");

        // Navigate to ProductListActivity
        btnViewProducts.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerMainActivity.this, Product_list.class); // Ensure `Product_list` matches class name
            startActivity(intent);
        });

        // Check product availability
        btnCheckAvailability.setOnClickListener(v -> checkProductAvailability());

        // Buy products (place order)
        btnBuyProducts.setOnClickListener(v -> placeOrder("product_id_123", 2)); // Example usage

        // Track order
        btnTrackOrder.setOnClickListener(v -> trackOrder("order_id_123")); // Example usage

        // Submit feedback
        btnSubmitFeedback.setOnClickListener(v -> submitFeedback("product_id_123", 5, "Great product!")); // Example usage

        // Logout functionality
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(CustomerMainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void checkProductAvailability() {
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isAvailable = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            int quantity = document.getLong("quantity").intValue();
                            if (quantity > 0) {
                                isAvailable = true;
                                break;
                            }
                        }
                        String message = isAvailable ? "Some products are available!" : "All products are out of stock.";
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error fetching product data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void placeOrder(String productId, int quantity) {
        db.collection("orders")
                .add(new Order(productId, quantity, "Processing")) // Order status set to "Processing"
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to place order: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void trackOrder(String orderId) {
        db.collection("orders")
                .document(orderId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String orderStatus = task.getResult().getString("orderStatus");
                        if (orderStatus != null) {
                            Toast.makeText(this, "Order status: " + orderStatus, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Order status not found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error tracking order.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitFeedback(String productId, int rating, String comment) {
        db.collection("feedback")
                .add(new Feedback(productId, rating, comment))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to submit feedback: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
