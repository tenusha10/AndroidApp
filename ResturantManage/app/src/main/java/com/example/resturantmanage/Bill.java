package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Bill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);


        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
