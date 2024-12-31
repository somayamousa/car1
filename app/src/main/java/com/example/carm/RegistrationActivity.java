package com.example.carm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationActivity extends AppCompatActivity {
    EditText username, password, email_txt, number_txt;
    Spinner gender_spn;
    Button signUpButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupViews();
    }

    private void setupViews() {
        username = findViewById(R.id.edttxt_userName);
        email_txt = findViewById(R.id.email_txt);
        password = findViewById(R.id.password);
        gender_spn = findViewById(R.id.gender_spn);
        number_txt = findViewById(R.id.edttxt_phoneN);
        signUpButton = findViewById(R.id.singUp_btn);
        signInButton = findViewById(R.id.signIn_btn);

        String[] genderChoice = {"Select Your Gender", "Female", "Male"};



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAllChecked = checkInformation();
                if (isAllChecked) {
                    storeIntoDatabase();
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email_txt = text.getText().toString();
        return (!TextUtils.isEmpty(email_txt) && Patterns.EMAIL_ADDRESS.matcher(email_txt).matches());
    }

    private boolean checkInformation() {
        if (username.getText().toString().isEmpty()) {
            username.setError("Username is Required");
            return false;
        }

        if (email_txt.getText().toString().isEmpty()) {
            email_txt.setError("Email is Required");
            return false;
        } else if (!isEmail(email_txt)) {
            email_txt.setError("Please Enter a Valid Email Address");
            return false;
        }

        if (number_txt.getText().toString().isEmpty()) {
            number_txt.setError("Phone Number is Required");
            return false;
        }

        if (password.getText().toString().isEmpty() || password.getText().toString().length() < 6) {
            password.setError("Please Enter a Password With at Least 6 Characters");
            return false;
        }

        if (gender_spn.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void storeIntoDatabase() {
        boolean isAllChecked = checkInformation();
        if (isAllChecked) {
            // Get data from input fields
            String usernameValue = username.getText().toString();
            String email = email_txt.getText().toString();
            String phoneNumber = number_txt.getText().toString();
            String pass = password.getText().toString();
            String gender = gender_spn.getSelectedItem().toString();

            // Database connection
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                // Establish connection
                String dbUrl = "jdbc:mysql://your-database-url:3306/CarManagementSystem"; // Change with your database details
                String dbUser = "your-database-username";
                String dbPass = "your-database-password";
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

                // Prepare SQL query to insert customer
                String query = "INSERT INTO Customer (username, email, phoneNumber, password, gender, role) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);

                // Set values in the query
                stmt.setString(1, usernameValue);
                stmt.setString(2, email);
                stmt.setString(3, phoneNumber);
                stmt.setString(4, pass);
                stmt.setString(5, gender);
                stmt.setString(6, "Renter"); // Default role for customers

                // Execute the query
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Success
                    Toast.makeText(RegistrationActivity.this, "Customer added successfully!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Failed to add customer", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                // Handle any database errors
                Toast.makeText(RegistrationActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    // Handle closing exceptions
                    Toast.makeText(RegistrationActivity.this, "Error closing database connection: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void clearInputFields() {
        username.setText("");
        email_txt.setText("");
        number_txt.setText("");
        password.setText("");
        gender_spn.setSelection(0);
    }
}
