package com.example.carm;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateCarActivity extends AppCompatActivity {

    private EditText editTextCarMake;
    private EditText editTextCarModel;
    private EditText editTextCarYear;
    private EditText editTextCarVIN;
    private Button buttonUploadPhoto;
    private Button buttonUpdateCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_car);

        // Initialize views
        editTextCarMake = findViewById(R.id.editTextCarMake);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        editTextCarYear = findViewById(R.id.editTextCarYear);
        editTextCarVIN = findViewById(R.id.editTextCarVIN);
        buttonUploadPhoto = findViewById(R.id.buttonUploadPhoto);
        buttonUpdateCar = findViewById(R.id.buttonUpdateCar);

        // Set up upload photo button
        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to upload car photo
                Toast.makeText(UpdateCarActivity.this, "Upload photo functionality not implemented", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up update car button
        buttonUpdateCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCarDetails();
            }
        });
    }

    private void updateCarDetails() {
        String carMake = editTextCarMake.getText().toString().trim();
        String carModel = editTextCarModel.getText().toString().trim();
        String carYear = editTextCarYear.getText().toString().trim();
        String carVIN = editTextCarVIN.getText().toString().trim();

        if (carMake.isEmpty() || carModel.isEmpty() || carYear.isEmpty() || carVIN.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logic to update car details in the database
        // For demonstration purposes, just showing a success message
        Toast.makeText(this, "Car details updated successfully", Toast.LENGTH_SHORT).show();
    }
}
