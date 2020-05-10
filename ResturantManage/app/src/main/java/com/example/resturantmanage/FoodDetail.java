package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.resturantmanage.Database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity {

    TextView food_name , food_price , food_description;
    ImageButton btnadd;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor= mPreferences.edit();

        //Initialise

        //firebase code
        database = FirebaseDatabase.getInstance();
        foods= database.getReference("Menu");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnadd = (ImageButton) findViewById(R.id.btnAddtocart);
        food_name =(TextView) findViewById(R.id.txtFoodDetailName);
        food_description=(TextView) findViewById(R.id.txtFoodDetailDesc);
        food_price=(TextView) findViewById(R.id.txtFoodDetailPrice);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adds the selected food item into the cart uses DB helper class
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice()

                ));
                Toast.makeText(FoodDetail.this,currentFood.getName()+"  added to cart ",Toast.LENGTH_SHORT).show();
            }
        });



        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }

    }

    //inflate menu so user can quickly check whats in the basket
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.basket,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_basket) {
            Intent intent = new Intent(FoodDetail.this, com.example.resturantmanage.OrderCart.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //function retreives data about the selected food item from the db
    private void getDetailFood(String foodId){
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
                food_price.setText("£"+currentFood.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "paused");
        mEditor.putString("FoodID",foodId);
        mEditor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "Resumed");
        String retreivedFoodID;
        retreivedFoodID=mPreferences.getString("FoodID",null);
        if(retreivedFoodID !=null){
            foodId=retreivedFoodID;
        }
    }

}
