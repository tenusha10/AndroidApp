package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TableView extends AppCompatActivity {
    ImageView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21;
    FirebaseDatabase database;
    DatabaseReference tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);

        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase code
        database= FirebaseDatabase.getInstance();
        tables =database.getReference("Table");


        //t1.setImageResource(R.drawable.red);
        loadAvailability();


    }

    public void loadAvailability(){
        tables.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Table item = postSnapshot.getValue(Table.class);
                    updateLight(item.getTableNo(),item.getAvailability());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateLight(String tableNo,String availability) {
        if(tableNo.equals("1")){
            t1 =(ImageView)findViewById(R.id.imgT1);
            if(availability.equals("0")){
                t1.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t1.setImageResource(R.drawable.red);
            }else{
                t1.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("2")){
            t2 =(ImageView)findViewById(R.id.imgT2);
            if(availability.equals("0")){
                t2.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t2.setImageResource(R.drawable.red);
            }else{
                t2.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("3")){
            t3 =(ImageView)findViewById(R.id.imgT3);
            if(availability.equals("0")){
                t3.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t3.setImageResource(R.drawable.red);
            }else{
                t3.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("4")){
            t4 =(ImageView)findViewById(R.id.imgT4);
            if(availability.equals("0")){
                t4.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t4.setImageResource(R.drawable.red);
            }else{
                t4.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("5")){
            t5 =(ImageView)findViewById(R.id.imgT5);
            if(availability.equals("0")){
                t5.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t5.setImageResource(R.drawable.red);
            }else{
                t5.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("6")){
            t6 =(ImageView)findViewById(R.id.imgT6);
            if(availability.equals("0")){
                t6.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t6.setImageResource(R.drawable.red);
            }else{
                t6.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("7")){
            t7 =(ImageView)findViewById(R.id.imgT7);
            if(availability.equals("0")){
                t7.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t7.setImageResource(R.drawable.red);
            }else{
                t7.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("8")){
            t8 =(ImageView)findViewById(R.id.imgT8);
            if(availability.equals("0")){
                t8.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t8.setImageResource(R.drawable.red);
            }else{
                t8.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("9")){
            t9 =(ImageView)findViewById(R.id.imgT9);
            if(availability.equals("0")){
                t9.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t9.setImageResource(R.drawable.red);
            }else{
                t9.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("10")){
            t10 =(ImageView)findViewById(R.id.imgT10);
            if(availability.equals("0")){
                t10.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t10.setImageResource(R.drawable.red);
            }else{
                t10.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("11")){
            t11 =(ImageView)findViewById(R.id.imgT11);
            if(availability.equals("0")){
                t11.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t11.setImageResource(R.drawable.red);
            }else{
                t11.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("12")){
            t12 =(ImageView)findViewById(R.id.imgT12);
            if(availability.equals("0")){
                t12.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t12.setImageResource(R.drawable.red);
            }else{
                t12.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("13")){
            t13 =(ImageView)findViewById(R.id.imgT13);
            if(availability.equals("0")){
                t13.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t13.setImageResource(R.drawable.red);
            }else{
                t13.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("14")){
            t14 =(ImageView)findViewById(R.id.imgT14);
            if(availability.equals("0")){
                t14.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t14.setImageResource(R.drawable.red);
            }else{
                t14.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("15")){
            t15 =(ImageView)findViewById(R.id.imgT15);
            if(availability.equals("0")){
                t15.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t15.setImageResource(R.drawable.red);
            }else{
                t15.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("16")){
            t16 =(ImageView)findViewById(R.id.imgT16);
            if(availability.equals("0")){
                t16.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t16.setImageResource(R.drawable.red);
            }else{
                t16.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("17")){
            t17 =(ImageView)findViewById(R.id.imgT17);
            if(availability.equals("0")){
                t17.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t17.setImageResource(R.drawable.red);
            }else{
                t17.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("18")){
            t18=(ImageView)findViewById(R.id.imgT18);
            if(availability.equals("0")){
                t18.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t18.setImageResource(R.drawable.red);
            }else{
                t18.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("19")){
            t19 =(ImageView)findViewById(R.id.imgT19);
            if(availability.equals("0")){
                t19.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t19.setImageResource(R.drawable.red);
            }else{
                t19.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("20")){
            t20 =(ImageView)findViewById(R.id.imgT20);
            if(availability.equals("0")){
                t20.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t20.setImageResource(R.drawable.red);
            }else{
                t20.setImageResource(R.drawable.blue);
            }
        }
        if(tableNo.equals("21")){
            t21 =(ImageView)findViewById(R.id.imgT21);
            if(availability.equals("0")){
                t21.setImageResource(R.drawable.green);
            }else if (availability.equals("1")){
                t21.setImageResource(R.drawable.red);
            }else{
                t21.setImageResource(R.drawable.blue);
            }
        }
    }



}
