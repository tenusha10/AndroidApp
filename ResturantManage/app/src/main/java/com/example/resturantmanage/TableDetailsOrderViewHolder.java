package com.example.resturantmanage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//view holder for recycler view in  table details
public class TableDetailsOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderNo,txtOrderTotal;
    public TableDetailsOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderNo=(TextView)itemView.findViewById(R.id.txtTableDetailsOrderNo);
        txtOrderTotal=(TextView)itemView.findViewById(R.id.txtTableDetailTotal);
    }

    @Override
    public void onClick(View v) {

    }
}
