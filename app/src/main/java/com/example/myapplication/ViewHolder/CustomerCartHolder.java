package com.example.myapplication.ViewHolder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.SetOrder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nameofitem,priceofItem,quanitiy;



    private ItemClickListener itemClickListener;



    public CartHolder(@NonNull View itemView) {
        super(itemView);
        nameofitem = (TextView) itemView.findViewById(R.id.nameofItem);
        priceofItem = (TextView) itemView.findViewById(R.id.priceofItem);
        quanitiy = (TextView) itemView.findViewById(R.id.priceofItem);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CustomerCartHolder extends RecyclerView.Adapter<CartHolder>{

    private List<SetOrder> listData = new ArrayList<>();
    private Context context;



    public CustomerCartHolder(List<SetOrder> listData,Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_for_cart,parent,false);
        return new CartHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getAmount()));
        holder.priceofItem.setText(fmt.format(price));
        holder.nameofitem.setText(listData.get(position).getNameofFood());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
