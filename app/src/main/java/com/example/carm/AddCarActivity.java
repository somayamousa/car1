package com.example.carm;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddCarActivity extends Activity {

    private EditText editTextCarMake, editTextCarModel, editTextCarYear, editTextCarVIN;
    private Button buttonUploadPhoto, buttonAddCar;
    private Uri carPhotoUri; // To store the URI of the selected car photo

    private static final int IMAGE_PICK_CODE = 1000; // Request code for picking an image
    private static final String UPLOAD_URL = "http://yourserver.com/uploadCar.php";  // Your PHP script URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);

        // Initialize views
        editTextCarMake = findViewById(R.id.editTextCarMake);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        editTextCarYear = findViewById(R.id.editTextCarYear);
        editTextCarVIN = findViewById(R.id.editTextCarVIN);
        buttonUploadPhoto = findViewById(R.id.buttonUploadPhoto);
        buttonAddCar = findViewById(R.id.buttonAddCar);

        // Handle car photo upload
        buttonUploadPhoto.setOnClickListener(v -> openImagePicker());

        // Handle car addition
        buttonAddCar.setOnClickListener(v -> addCar());
    }

    // Open image picker for selecting car photo
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && requestCode == IMAGE_PICK_CODE) {
            // Get the URI of the selected image
            carPhotoUri = data.getData();
            Toast.makeText(this, "Photo selected successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to add the car to the database
    private void addCar() {
        String make = editTextCarMake.getText().toString().trim();
        String model = editTextCarModel.getText().toString().trim();
        String yearString = editTextCarYear.getText().toString().trim();
        String vin = editTextCarVIN.getText().toString().trim();

        // Check if the fields are empty
        if (make.isEmpty() || model.isEmpty() || yearString.isEmpty() || vin.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the car year (should be a number)
        int year;
        try {
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid year format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert car photo URI to file path if it is available
        String carPhotoPath = null;
        if (carPhotoUri != null) {
            carPhotoPath = getRealPathFromURI(carPhotoUri); // Get real file path from URI
        }

        // Send car details to the server
        sendCarDetailsToServer(make, model, year, vin, carPhotoPath);
    }

    // Method to get real file path from URI
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {android.provider.MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    // Method to send car details to the server using PHP API
    private void sendCarDetailsToServer(String make, String model, int year, String vin, String carPhotoPath) {
        StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_URL,
                response -> Toast.makeText(AddCarActivity.this, "Car added successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(AddCarActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("make", make);
                params.put("model", model);
                params.put("year", String.valueOf(year));
                params.put("vin", vin);
                params.put("car_photo", carPhotoPath);  // You might want to use a different way to upload the photo
                return params;
            }
        };

        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}

