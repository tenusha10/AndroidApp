package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.basket,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_basket) {
            Intent intent = new Intent(MenuHome.this, com.example.resturantmanage.OrderCart.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void StartersClicked(View view){
        Intent intent = new Intent(MenuHome.this, com.example.resturantmanage.MenuStarters.class);
        intent.putExtra("CategoryId","2");
        startActivity(intent);
    }
    public void MainsClicked(View view){
        Intent intent = new Intent(MenuHome.this, com.example.resturantmanage.MenuMains.class);
        intent.putExtra("CategoryId","3");
        startActivity(intent);
    }
    public void DrinksClicked(View view){
        Intent intent = new Intent(MenuHome.this, com.example.resturantmanage.MenuDrink.class);
        intent.putExtra("CategoryId","1");
        startActivity(intent);
    }
    public void DessertsClicked(View view){
        Intent intent = new Intent(MenuHome.this, com.example.resturantmanage.MenuDesserts.class);
        intent.putExtra("CategoryId","4");
        startActivity(intent);
    }
}
