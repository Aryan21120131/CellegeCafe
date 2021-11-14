package com.example.projecthackathon.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projecthackathon.R;

public class CanteenMainScreen extends AppCompatActivity {

    RecyclerView canteenRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_main_screen);
        hook();

    }

    private void hook() {
        canteenRecyclerView=findViewById(R.id.canteenRecyclerView);
    }
}