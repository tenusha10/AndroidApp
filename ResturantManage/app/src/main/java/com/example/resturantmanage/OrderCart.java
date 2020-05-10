package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resturantmanage.Database.Database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderCart extends AppCompatActivity {
    NumberPicker tableNumberPicker;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnSumbitOrder;
    int tableNumber;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //firebase code
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //initialise
        tableNumberPicker = findViewById(R.id.tipPercentage);
        tableNumberPicker.setMaxValue(20);
        tableNumberPicker.setMinValue(1);

        recyclerView= (RecyclerView) findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.txtTotal);
        btnSumbitOrder = (Button)findViewById(R.id.btnComfirm);

        loadCartItems();

        //adds order to the db linked to  a table number provided by user
        btnSumbitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.isEmpty()) {
                    Toast.makeText(OrderCart.this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
                } else {
                    tableNumber=  tableNumberPicker.getValue();
                    Request request = new Request(String.valueOf(tableNumber), txtTotalPrice.getText().toString(), cart);
                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                    new Database(getBaseContext()).clearCart();
                    Toast.makeText(OrderCart.this, "Order Comfirmed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderCart.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.clear_cart,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //removes the order from cart
        if(id == R.id.action_trash) {
            new Database(getBaseContext()).clearCart();
            Intent refresh = new Intent(OrderCart.this,OrderCart.class);
            startActivity(refresh);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //load contents from local db
    public void loadCartItems(){
        cart= new Database(this).retriveCart();
        adapter= new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //calc total for order
        double total = 0;
        for(Order order:cart)
            total += (Double.parseDouble(order.getPrice()))*(Double.parseDouble(order.getQuantity()));
        Locale locale = new Locale("en","GB");
        NumberFormat nfmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(nfmt.format(total));
    }
}
