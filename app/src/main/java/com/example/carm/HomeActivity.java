package com.example.carm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView iconAddCar = findViewById(R.id.iconAddCar);
        Button openProfile = findViewById(R.id.openProfile);

        // Navigate to AddCarActivity
        iconAddCar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddCarActivity.class);
            startActivity(intent);
        });

        // Navigate to ProfileActivity
        openProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}
