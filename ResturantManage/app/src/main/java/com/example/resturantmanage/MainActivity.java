package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resturantmanage.Database.Database;
import com.example.resturantmanage.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"No Internet! Please turn on Wifi or Data",Toast.LENGTH_SHORT).show();
        }
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
        String url ="https://www.food.gov.uk/sites/default/files/media/document/thinkallergy.pdf";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
