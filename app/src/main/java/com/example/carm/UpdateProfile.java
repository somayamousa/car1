package com.example.carm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfile extends AppCompatActivity {
    EditText fName, lName, password_edt, phoneN;
    Button save, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile); // Ensure you have this layout file created

        // Initialize views


        // Set up click listeners
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    saveProfileData();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous screen
                finish();
            }
        });
    }

    private boolean validateInputs() {
        if (fName.getText().toString().isEmpty()) {
            fName.setError("First name is required");
            return false;
        }

        if (lName.getText().toString().isEmpty()) {
            lName.setError("Last name is required");
            return false;
        }

        if (phoneN.getText().toString().isEmpty()) {
            phoneN.setError("Phone number is required");
            return false;
        }

        if (password_edt.getText().toString().isEmpty() || password_edt.getText().toString().length() < 6) {
            password_edt.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private void saveProfileData() {
        // Extract data from EditText fields
        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        String phoneNumber = phoneN.getText().toString();
        String password = password_edt.getText().toString();

        // Simulate saving profile data (replace with actual database logic if needed)
        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        clearInputFields();
    }

    private void clearInputFields() {
        fName.setText("");
        lName.setText("");
        phoneN.setText("");
        password_edt.setText("");
    }
}
