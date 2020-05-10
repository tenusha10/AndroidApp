package com.example.resturantmanage;

import  android.view.View;

//interface to handle onclick on the recycler view items
public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);

}

