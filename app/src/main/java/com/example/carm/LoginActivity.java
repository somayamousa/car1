package com.example.carm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {

    TextView welcomeText, signInText;
    EditText usernameText, passwordText;
    Button signInButton, signUpButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeText = findViewById(R.id.welcomeText);
        signInText = findViewById(R.id.signInText);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        signInButton.setOnClickListener(v -> {
            checkDatabase();
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    void checkDatabase() {
        final String email = usernameText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();

        // Validate input fields
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check in the Manager and Customer tables
        checkManagerLogin(email, password);
    }

    void checkManagerLogin(String email, String password) {
        new Thread(() -> {
            try {
                // JDBC connection string
                String dbUrl = "jdbc:mysql://<your-server-ip>:3306/CarManagementSystem";
                String dbUsername = "<your-db-username>";
                String dbPassword = "<your-db-password>";

                // Establish the connection
                Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

                // Prepare SQL query for Manager
                String query = "SELECT * FROM Manager WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Login successful, Manager found
                    String role = "Admin";  // Role is Manager in this case
                    saveUserData(email, role);
                    runOnUiThread(() -> {
                      //  Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                      //  startActivity(intent);
                        finish();
                    });
                } else {
                    // If Manager login fails, check Customer table
                    checkCustomerLogin(email, password);
                }
            } catch (Exception e) {
                Log.e("LoginError", "Error checking Manager login: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    void checkCustomerLogin(String email, String password) {
        new Thread(() -> {
            try {
                // JDBC connection string
                String dbUrl = "jdbc:mysql://<your-server-ip>:3306/CarManagementSystem";
                String dbUsername = "<your-db-username>";
                String dbPassword = "<your-db-password>";

                // Establish the connection
                Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

                // Prepare SQL query for Customer
                String query = "SELECT * FROM Customer WHERE email = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Login successful, Customer found
                    String role = "Lessor";  // Role is Customer in this case
                    saveUserData(email, role);
                    runOnUiThread(() -> {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    // User not found
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "User not found or incorrect password", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("LoginError", "Error checking Customer login: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    // Method to save user's email and role in SharedPreferences
    void saveUserData(String email, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.putString("user_role", role);
        editor.apply();
    }
}
