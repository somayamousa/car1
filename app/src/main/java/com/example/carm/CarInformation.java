package com.example.carm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class CarInformation extends AppCompatActivity {
    private static final String TAG = "CarInformation";
    private static final long ONE_MEGABYTE = 5 * 1024 * 1024;

    private Button btn_rent;
    private ImageView car_img;
    private TextView title_txt, carMake, carModel, carYear, carPrice;
    private RecyclerView recyclerView_xml;

    private String carID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_information);


    }
}
