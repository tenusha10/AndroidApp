package com.example.resturantmanage;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mName;
    public TextView mDesc;
    public TextView mPrice;
    //public ImageButton addButton;

    private com.example.resturantmanage.ItemClickListener itemClickListener;

    public void setItemClickListener(com.example.resturantmanage.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.txtName);
        mDesc = (TextView) itemView.findViewById(R.id.txtDesc);
        mPrice = (TextView) itemView.findViewById(R.id.txtPrice);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
