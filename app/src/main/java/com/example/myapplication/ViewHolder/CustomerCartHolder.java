package com.example.myapplication.ViewHolder;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CartInfo;
import com.example.myapplication.CustomerCart;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CustomerCartHolder extends RecyclerView.Adapter{


    List<CartInfo> cartInfoList;
    float printprice;

    public CustomerCartHolder(List<CartInfo> cartInfoList) {
        this.cartInfoList = cartInfoList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_cart,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        CartInfo cartInfo = cartInfoList.get(position);
        viewHolderClass.name.setText(cartInfo.getFoodName());
        viewHolderClass.eliminateiteam.setText(cartInfo.getEliminateIngredients());
        viewHolderClass.totalprice.setText(cartInfo.getTotalFoodPrice());
        viewHolderClass.quanitiy.setText(cartInfo.getQuantity());


    }

    @Override
    public int getItemCount() {
        return cartInfoList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name, eliminateiteam, totalprice, quanitiy, TotalorderPrice,Tax,PriceafterTax;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameofItem);
            eliminateiteam = itemView.findViewById(R.id.quantity2);
            totalprice = itemView.findViewById(R.id.priceofItem);
            quanitiy = itemView.findViewById(R.id.quantity);
            TotalorderPrice = itemView.findViewById(R.id.totalpricebeforetax);
            Tax = itemView.findViewById(R.id.Tax);
            PriceafterTax = itemView.findViewById(R.id.totalprice);
        }
    }
}




