package com.example.resturantmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //initialise custom view holder for the recycler view of the Cart feature
    public TextView cartItemName;
    public TextView cartItemQty;
    public TextView cartItemPrice;

    public CartViewHolder(View itemView) {
        super(itemView);
        cartItemName =(TextView) itemView.findViewById(R.id.txtOrderItemName);
        cartItemQty =(TextView) itemView.findViewById(R.id.txtOrderItemQty);
        cartItemPrice =(TextView) itemView.findViewById(R.id.txtOrderItemPrice);

    }

    @Override
    public void onClick(View v) {

    }
}
public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    //binds the data into the view holder
    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        //formats the strings to contain currency type "£"
        Locale locale = new Locale("en","GB");
        NumberFormat nfmt = NumberFormat.getCurrencyInstance(locale);
        double price = (Double.parseDouble(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.cartItemPrice.setText(nfmt.format(price));
        holder.cartItemName.setText(listData.get(position).getProductName());
        holder.cartItemQty.setText(listData.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
