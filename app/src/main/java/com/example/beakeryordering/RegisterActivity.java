package com.example.beakeryordering;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private RadioGroup rgRole;
    private Button btnRegister;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rgRole = findViewById(R.id.rgRole);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");

        // Register button click listener
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        // Fetch user input
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        int selectedRoleId = rgRole.getCheckedRadioButtonId();

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields.");
            return;
        }
        if (selectedRoleId == -1) {
            showToast("Please select a role.");
            return;
        }

        // Determine selected role
        RadioButton selectedRole = findViewById(selectedRoleId);
        String role = selectedRole.getText().toString();

        // Create a new user in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String userId = authResult.getUser().getUid();

                    // Create User object and save to Realtime Database
                    User user = new User(name, email, role);
                    saveUserToDatabase(userId, user);
                })
                .addOnFailureListener(e -> showToast("Registration failed: " + e.getMessage()));
    }

    private void saveUserToDatabase(String userId, User user) {
        database.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    showToast("Registration successful!");
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> showToast("Database error: " + e.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
