package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Bill extends AppCompatActivity {
    String Tablenumber;
    String InitialTotal;
    FirebaseDatabase database;
    DatabaseReference foodlist;
    TextView tableNumber, txtTotal, ppCost;
    ArrayAdapter arrayAdapter;
    ListView lstFood;
    NumberPicker tipPercentage, NoPeople;
    Button Calctotal, billTable;
    DatabaseReference tablereq;
    DatabaseReference orderReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //orderedFood = getIntent().getData("BillContents");
        //orderedFood = getIntent().getStringExtra("BillContents");
        if(getIntent() != null) {
            Tablenumber = getIntent().getStringExtra("TableNo");
            InitialTotal = getIntent().getStringExtra("Total");

        }

        database = FirebaseDatabase.getInstance();
        foodlist=database.getReference("Requests");
        orderReq=database.getReference("Requests");
        getBillItems();

        tableNumber= (TextView)findViewById(R.id.txtBillTableNumber);
        tableNumber.setText("Table "+Tablenumber);

        txtTotal=(TextView)findViewById(R.id.txtToPay);
        txtTotal.setText("Total to Pay(20% VAT) £"+InitialTotal);

        ppCost=(TextView)findViewById(R.id.txtPPCost);
        ppCost.setText(InitialTotal);

        lstFood=(ListView)findViewById(R.id.billSummaryList);

        //initialise
        NoPeople = findViewById(R.id.splitnumber);
        NoPeople.setMaxValue(20);
        NoPeople.setMinValue(1);

        tipPercentage = findViewById(R.id.tipPercentage);
        tipPercentage.setMaxValue(50);
        tipPercentage.setMinValue(0);


        Calctotal =(Button)findViewById(R.id.btnCalculate);
        Calctotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SplitNumber;
                double costPP,Percentage;
                double newTotal;
                SplitNumber=NoPeople.getValue();
                Percentage=tipPercentage.getValue();
                newTotal=Double.parseDouble(InitialTotal)+((Percentage/100)*Double.parseDouble(InitialTotal));
                txtTotal.setText("Total to Pay(20% VAT) £"+Double.toString(newTotal));
                costPP=(newTotal/SplitNumber);
                ppCost.setText(Double.toString(costPP));
            }
        });

        billTable=(Button)findViewById(R.id.btnFinalBillTable);
        billTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database =FirebaseDatabase.getInstance();
                tablereq=database.getReference("Table");
                tablereq.child(Tablenumber).child("availability").setValue("0");
                DeleteOrders();
                Toast.makeText(Bill.this,"Table Released! Thanks Payment Accepted ",Toast.LENGTH_SHORT).show();
                Intent home = new Intent(Bill.this,TableView.class);
                startActivity(home);
            }
        });




    }
    public void DeleteOrders(){
        orderReq.orderByChild("tableNo").equalTo(Tablenumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot oldorder:dataSnapshot.getChildren()){
                    oldorder.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getBillItems() {
        foodlist.orderByChild("tableNo").equalTo(Tablenumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<List<String>> retreivedFood = new ArrayList<List<String>>();
                ArrayList<String> arrayList = new ArrayList<>();

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Request order = postSnapshot.getValue(Request.class);
                    retreivedFood.add(addOrder(order.getFoods()));
                    /*retreivedFood.add(order.getFoods());
                    orderedFood.add(order.getFoods());
                    Log.d("i",orderedFood.get(0).get(0).getProductName()); */
                }
                arrayList=joinArrays(retreivedFood);
                arrayAdapter=getArrayAdapter(arrayList);
                lstFood.setAdapter(arrayAdapter);
                //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //Log.d("i",retreivedFood.get(0).get(0).getProductName());
        //return retreivedFood;
    }

    private ArrayAdapter getArrayAdapter(ArrayList<String> arrayList) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        return arrayAdapter;
    }

    private ArrayList<String> joinArrays(ArrayList<List<String>> retreivedFood) {
        ArrayList<String> jArray = new ArrayList<>();
        for(int i =0 ; i<retreivedFood.size();i++){
            for(int j=0;j<retreivedFood.get(i).size();j++){
                jArray.add(retreivedFood.get(i).get(j));
            }
        }
        return jArray;
    }

    private  List<String>  addOrder(List<Order> foods) {
        //orderedFood.add(foods);
        List<String> d = new ArrayList<>();
        String row;
        double updatedPrice;
        for(Order o : foods){
            updatedPrice=(Double.parseDouble(o.getPrice()))*(Integer.parseInt(o.getQuantity()));
            row = o.getProductName().toString() +"  " +"Qty:  "+o.getQuantity().toString()+" Price: £"+Double.toString(updatedPrice);
            d.add(row);
        }
        return d;
    }

}
