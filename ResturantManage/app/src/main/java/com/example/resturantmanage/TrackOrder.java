package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrackOrder extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public List<Order> foods;
    ArrayAdapter arrayAdapter;

    ListView listView;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database=FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        loadOrders();


    }

    private void loadOrders() {
        adapter= new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orders_list,
                OrderViewHolder.class,
                requests.orderByChild("TableNo")
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {
                orderViewHolder.txtOrderNo.setText("OrderNo:"+adapter.getRef(i).getKey());
                orderViewHolder.txtTableNo.setText("Table "+request.getTableNo());
                foods =request.getFoods();
                ArrayList<String> data1;
                arrayAdapter=loadOrderFoodList(foods);
                orderViewHolder.listViewFood.setAdapter(arrayAdapter);


            }
        };
        recyclerView.setAdapter(adapter);
    }

    private ArrayAdapter loadOrderFoodList(List<Order> foods) {
        ArrayList<String> arrayList = new ArrayList<>();
        String row;
        for(Order o : foods){
            row = "Name :  "+o.getProductName().toString() +"     " +"Qty:  "+o.getQuantity().toString();
            arrayList.add(row);
            Log.d("T",row);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        return arrayAdapter;
    }
}
