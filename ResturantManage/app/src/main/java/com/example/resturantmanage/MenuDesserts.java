package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MenuDesserts extends AppCompatActivity {

    //Initialise
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String categoryID ="";

    FirebaseDatabase database;
    DatabaseReference foodList;
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_desserts);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        categoryID= getIntent().getStringExtra("CategoryId");
        Log.i("TEST",categoryID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //firebase code
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Menu");


        recyclerView =(RecyclerView) findViewById(R.id.dessertList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        if(!categoryID.isEmpty() && categoryID!=null){
            loadListFood(categoryID);
        }

        //implement a search bar to quickly search for food items from the recycler view
        materialSearchBar =(MaterialSearchBar)findViewById(R.id.searchBarDesserts);
        materialSearchBar.setHint("Enter your Food");
        loadSuggestions();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //change suggest list when new text is typed
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //restore original search adapter
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //show search results
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

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
            Intent intent = new Intent(MenuDesserts.this, com.example.resturantmanage.OrderCart.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //function binds the adapter to the recycler view after reading contents off the db
    private void loadListFood(String categoryID) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_list_item,FoodViewHolder.class,foodList.orderByChild("MenuID").equalTo(categoryID)) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.mName.setText(food.getName());
                foodViewHolder.mPrice.setText("£"+food.getPrice());
                foodViewHolder.mDesc.setText(food.getDescription());

                final Food local = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MenuDesserts.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(MenuDesserts.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    //performs a search and attaches  a new adapter to the recycler view containing the results
    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_list_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.mName.setText(food.getName());
                foodViewHolder.mPrice.setText("£"+food.getPrice());
                foodViewHolder.mDesc.setText(food.getDescription());

                final Food local = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MenuDesserts.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(MenuDesserts.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);

    }

    //functions attaches suggestion to the search so the user can execute a quick search
    private void loadSuggestions() {
        foodList.orderByChild("MenuID").equalTo(categoryID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Food item = postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
