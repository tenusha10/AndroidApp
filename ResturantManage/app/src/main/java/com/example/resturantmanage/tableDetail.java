package com.example.resturantmanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class tableDetail extends AppCompatActivity {
    String Tablenumber="";
    TextView DisplayTableNo, txtT;
    ImageButton OrderFood, occupyTable, releaseTable,serveFood,billTable;
    double t=0;
    double NewTotal=0;
    String strT="";

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference tablereq;
    FirebaseRecyclerAdapter<Request,TableDetailsOrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //firebase
        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");


        OrderFood = (ImageButton)findViewById(R.id.btnOrderMoreFood);
        OrderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tableDetail.this, MenuHome.class);
                startActivity(intent);
            }
        });

        occupyTable=(ImageButton)findViewById(R.id.btnOccupyTable);
        occupyTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database =FirebaseDatabase.getInstance();
                tablereq=database.getReference("Table");
                tablereq.child(Tablenumber).child("availability").setValue("1");
                Toast.makeText(tableDetail.this,"Table Reserved",Toast.LENGTH_SHORT).show();



            }
        });

        releaseTable=(ImageButton)findViewById(R.id.btnReleaseTable);
        releaseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database =FirebaseDatabase.getInstance();
                tablereq=database.getReference("Table");
                tablereq.child(Tablenumber).child("availability").setValue("0");
                Toast.makeText(tableDetail.this,"Table Released",Toast.LENGTH_SHORT).show();
            }
        });

        serveFood=(ImageButton)findViewById(R.id.btnServeFood);
        serveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database =FirebaseDatabase.getInstance();
                tablereq=database.getReference("Table");
                tablereq.child(Tablenumber).child("availability").setValue("2");
                Toast.makeText(tableDetail.this,"Food served Comfirmed! ",Toast.LENGTH_SHORT).show();
            }
        });

        billTable=(ImageButton)findViewById(R.id.btnBillTable);
        billTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tableDetail.this,Bill.class);
                intent.putExtra("TableNo",Tablenumber);
                intent.putExtra("Total",NewTotal);
                startActivity(intent);
            }
        });




            Tablenumber = getIntent().getStringExtra("TableNo");
            //Log.w("Test", Tablenumber);
            DisplayTableNo = (TextView) findViewById(R.id.txtDetailTableNo);
            DisplayTableNo.setText("Table " + Tablenumber);


        //recycler view
        recyclerView=(RecyclerView)findViewById(R.id.tableordersList);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders(Tablenumber);

        txtT = (TextView)findViewById(R.id.txtTableTotal);




    }

    private void loadOrders(String tnumber) {
        adapter= new FirebaseRecyclerAdapter<Request, TableDetailsOrderViewHolder>(
                Request.class,
                R.layout.tabledetails_orderlayout,
                TableDetailsOrderViewHolder.class,
                requests.orderByChild("tableNo").equalTo(tnumber)

        ) {
            @Override
            protected void populateViewHolder(TableDetailsOrderViewHolder tableDetailsOrderViewHolder, Request request, int i) {
                tableDetailsOrderViewHolder.txtOrderNo.setText("Order#: "+adapter.getRef(i).getKey());
                tableDetailsOrderViewHolder.txtOrderTotal.setText(request.getTotal());
                strT=request.getTotal().substring(1);
                t+=(Double.parseDouble(strT));
                Log.d("msg",Double.toString(t));
                txtT.setText("£"+Double.toString(t));
                NewTotal=t;

            }
        };
        recyclerView.setAdapter(adapter);
    }
}
