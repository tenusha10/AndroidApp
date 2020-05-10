package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    //Handing onclicks of the image buttons to navigate to different pages
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
        Intent intent = new Intent(MainActivity.this, com.example.resturantmanage.TableView.class);
        Toast.makeText(this,"Please Select the Table To be Billed",Toast.LENGTH_LONG).show();
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

    @Override
    protected void onStart() {
        super.onStart();

        //App requires Internet connection to read and write to firebase therefore the app checks if the device is connected to the internet and make the user aware
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"No Internet! Please turn on Wifi or Data",Toast.LENGTH_SHORT).show();
        }
    }

    //infalte the action menu and handle onclicks
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.info,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //directs to the webview
        if(id == R.id.info) {
            Intent intent = new Intent(this, AppInstructions.class);
            startActivity(intent);
            return true;
        }
        //directs to custom view to take a quick note
        if(id == R.id.note) {
            Intent intent = new Intent(this, QuickNote.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
