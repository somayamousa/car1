package com.example.carm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView text;
    private Animation top;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupView();
    }

    private void setupView() {
        logo = findViewById(R.id.splashLogo);
        text = findViewById(R.id.splashText);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        logo.setAnimation(top);

        final String animateText = text.getText().toString();
        text.setText("");

        // Animate text
        new Handler().postDelayed(() -> {
            for (char c : animateText.toCharArray()) {
                text.append(String.valueOf(c));
                try {
                    Thread.sleep(150); // Adjust the speed as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 150);

        // Navigate after delay
        new Handler().postDelayed(() -> {
            Intent intent;
            if (userId == null || userId.isEmpty()) {
                intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 7000);
    }
}
