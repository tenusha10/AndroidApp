package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
//import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Query;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Bill extends AppCompatActivity {
    String Tablenumber;
    String InitialTotal;
    String voucher1=null;
    String voucher2=null;
    FirebaseDatabase database;
    DatabaseReference foodlist;
    TextView tableNumber, txtTotal, ppCost;
    ArrayAdapter arrayAdapter;
    ListView lstFood;
    NumberPicker tipPercentage, NoPeople;
    Button Calctotal, billTable;
    DatabaseReference tablereq;
    DatabaseReference orderReq;
    ImageButton ScanVoucher;
    boolean v1=false;
    String status;



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


            tableNumber= (TextView)findViewById(R.id.txtBillTableNumber);
            tableNumber.setText("Table "+Tablenumber);

            txtTotal=(TextView)findViewById(R.id.txtToPay);
            txtTotal.setText("Total to Pay(20% VAT) £"+InitialTotal);

            if(getIntent().getStringExtra("status") !=null) {
                status = getIntent().getStringExtra("status");
                if (status.equals("true")) {
                    v1 = true;
                    double t;
                    t=(Double.parseDouble(InitialTotal)*0.9);
                    InitialTotal=Double.toString(t);
                    Log.d("bar",Double.toString(t));
                    txtTotal.setText("Total to Pay(20% VAT) £"+InitialTotal);
                    Toast.makeText(Bill.this,"Voucher Accepted",Toast.LENGTH_SHORT).show();
                }
            }
        }

        database = FirebaseDatabase.getInstance();
        foodlist=database.getReference("Requests");
        orderReq=database.getReference("Requests");
        getBillItems();



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

        ScanVoucher=(ImageButton)findViewById(R.id.btnVoucher);
        ScanVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bill.this,barcodeView.class);
                i.putExtra("TableNo",Tablenumber);
                i.putExtra("Total",InitialTotal);
                startActivity(i);

            }
        });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shareaction,menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
        return super.onCreateOptionsMenu(menu);

    }

    private Intent createShareIntent() {
        String emailbody="";
        String line="";
        for (int i=0;i<arrayAdapter.getCount();i++){
            line= arrayAdapter.getItem(i).toString();
            emailbody=emailbody+line;
        }
        String t="   "+ txtTotal.getText().toString();
        emailbody=emailbody+t;
        Log.d("email body",emailbody);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,emailbody);
        return shareIntent;
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
                }
                arrayList=joinArrays(retreivedFood);
                arrayAdapter=getArrayAdapter(arrayList);
                lstFood.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
