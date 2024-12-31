package com.example.carm;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and other ImageViews
        recyclerView = findViewById(R.id.recycler_layout);

        ImageView homeIcon = findViewById(R.id.homeIcon);
        ImageView aboutIcon = findViewById(R.id.aboutIcon);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Set click listeners for each icon

        // If you want to activate the search feature, uncomment this section and set the correct activity


        // About Us icon listener
        aboutIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        });

        // Profile icon listener
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // If the home icon is supposed to have an action, add it here
        homeIcon.setOnClickListener(v -> {
            // You can specify what action you want when the home icon is clicked
            // Example: navigate to HomeActivity or just go back
            Intent intent = new Intent(MainActivity.this, HomeActivity.class); // Example
            startActivity(intent);
        });
    }
}
