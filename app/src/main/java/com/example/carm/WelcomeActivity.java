package com.example.carm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize views
        ImageView welcomeImg = findViewById(R.id.welcome_img);
        TextView welcomeTxt = findViewById(R.id.welcome_txt);
        TextView welcomeText = findViewById(R.id.welcome_text);
        Button signInButton = findViewById(R.id.welcome_signin);
        Button signUpButton = findViewById(R.id.welcome_signup);

        // Set click listeners
        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}
