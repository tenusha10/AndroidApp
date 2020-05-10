package com.example.resturantmanage;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//view holder for recycler view in order cart
public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderNo, txtTableNo;
    public ListView listViewFood;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderNo= (TextView) itemView.findViewById(R.id.txtOrderNo);
        txtTableNo =(TextView) itemView.findViewById(R.id.txtDetailTableNo);
        listViewFood =(ListView)itemView.findViewById(R.id.listofOrderItems);

    }

    @Override
    public void onClick(View v) {

    }
}

