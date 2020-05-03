package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.resturantmanage.Database.Database;
import com.example.resturantmanage.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MenuClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.MenuHome.class);
        startActivity(intent);
    }

    public void OrderClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.OrderCart.class);
        startActivity(intent);
    }

    public void TableViewClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.TableView.class);
        startActivity(intent);
    }

    public void BillClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.Bill.class);
        startActivity(intent);
    }

    public void TrackClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.TrackOrder.class);
        startActivity(intent);
    }

    public void EditClicked(View view){
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.EditOrder.class);
        startActivity(intent);
    }

}
