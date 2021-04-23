package com.example.myapplication.inventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class orderFromSupplierActivity extends AppCompatActivity {

    private TextView formTotal;
    private RecyclerView recyclerView;
    private DatabaseReference inventory;
    private FirebaseRecyclerAdapter<Items, OrderFormAdapter.OrderFormViewHolder> firebaseRecyclerAdapter;
    private ArrayList<ArrayList<String>> data;
    private Button buttonCreateForm;
    private Button buttonFillOrder;

    public void updateFormTotal() {
        float runningTotal = 0;
        for(ArrayList<String> row : data) {
            runningTotal += Float.parseFloat(row.get(3).substring(1));
        }
        formTotal.setText(String.format("$%.2f", runningTotal));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_from_supplier);
        formTotal = findViewById(R.id.textViewTotalAmt);
        buttonCreateForm = findViewById(R.id.buttonCreateOrderForm);
        buttonFillOrder = findViewById(R.id.buttonFillOrder);
        recyclerView = findViewById(R.id.recyclerViewOrderForm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        inventory = FirebaseDatabase.getInstance().getReference("Inventory");


        inventory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = new ArrayList<ArrayList<String>>();
                for(DataSnapshot item : snapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) item.getValue();
                        int count = Integer.parseInt(String.valueOf(map.get("count")));
                        int min = Integer.parseInt(String.valueOf(map.get("min")));
                        int numToBuy = (min - count + 5);
                        if(numToBuy > 0) {
                            String name = String.valueOf(map.get("name"));
                            float price = Float.parseFloat(String.valueOf(map.get("price")));
                            String priceStr = String.format("$%.2f", price);
                            String total = String.format("$%.2f", price*numToBuy);

                            ArrayList<String> row = new ArrayList<String>();
                            row.add(String.valueOf(numToBuy));
                            row.add(name);
                            row.add(priceStr);
                            row.add(total);
                            data.add(row);
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
                OrderFormAdapter adapter = new OrderFormAdapter(data);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                updateFormTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });

        buttonCreateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OrderForm." + System.currentTimeMillis() + ".csv");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    String output = "Order Form,,,\nQty,Name,Price,Total\n";
                    for(ArrayList<String> row : data) {
                        output += row.get(0) + "," + row.get(1) + "," + row.get(2) + "," + row.get(3);
                        output += "\n";
                    }
                    output += "Total:," + formTotal.getText() + ",,\n";
                    fos.write(output.getBytes());
                    fos.close();
                } catch (Exception e) {
                    Toast.makeText(orderFromSupplierActivity.this, "Order Form Creation Failed!", Toast.LENGTH_LONG);
                }
                Toast.makeText(orderFromSupplierActivity.this, "Order Form Created in Documents Folder!", Toast.LENGTH_LONG);
            }
        });

        buttonFillOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventory.runTransaction(new Transaction.Handler() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        for(ArrayList<String> row : data) {
                            String name = row.get(1);
                            int qty;
                            try {
                                 qty = Integer.parseInt(row.get(0));
                            } catch(Exception e) {
                                qty = 0;
                            }
                            for(MutableData inventoryItemData : currentData.getChildren()) {
                                Map<String, Object> inventoryItem = (Map<String, Object>) inventoryItemData.getValue();
                                String inventoryName = String.valueOf(inventoryItem.get("name"));
                                if(name.equalsIgnoreCase(inventoryName)) {
                                    long currCount = (long) inventoryItem.get("count");
                                    inventoryItem.replace("count", qty + currCount);
                                    inventoryItemData.setValue(inventoryItem);
                                    break;
                                }
                            }
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        Toast.makeText(orderFromSupplierActivity.this, "Order Filled Successfully!", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public class OrderFormAdapter extends RecyclerView.Adapter<OrderFormAdapter.OrderFormViewHolder> {

        private ArrayList<ArrayList<String>> mData;

        public OrderFormAdapter(ArrayList<ArrayList<String>> datas) {
            mData = datas;
        }

        @NonNull
        @Override
        public OrderFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventoryorder_list_layout, parent, false);
            return new OrderFormViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderFormViewHolder holder, int position) {
            ArrayList<String> row = mData.get(position);
            Log.d("TAG", row.get(0) + row.get(1) + row.get(2) + row.get(3));
            holder.setDetails(row.get(0), row.get(1), row.get(2), row.get(3));

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    PopupMenu popup = new PopupMenu(orderFromSupplierActivity.this, view);
                    popup.inflate(R.menu.selectinventoryitempopupmenu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch(menuItem.getTitle().toString()) {
                                case "Edit":
                                    Log.d("TAG","Edit clicked");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(orderFromSupplierActivity.this);
                                    builder.setTitle("Edit Item Qty");

                                    final EditText input = new EditText(orderFromSupplierActivity.this);
                                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    builder.setView(input);

                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String newQty = input.getText().toString();
                                            if(newQty.isEmpty()) newQty = "0";
                                            ArrayList<String> row = mData.get(position);
                                            row.set(0, newQty);
                                            float price = Float.parseFloat(row.get(2).substring(1));
                                            row.set(3, String.format("$%.2f", Integer.parseInt(newQty)*price));
                                            OrderFormAdapter.super.notifyDataSetChanged();
                                            updateFormTotal();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                    break;
                                case "Delete":
                                    Log.d("TAG","Delete clicked");
                                    mData.remove(position);
                                    OrderFormAdapter.super.notifyDataSetChanged();
                                    updateFormTotal();
                                    break;
                                default:
                                    Log.d("TAG","Cancel clicked");
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class OrderFormViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            View mView;
            ItemClickListener itemClickListener;

            public OrderFormViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                itemView.setOnClickListener(this);
            }

            public void setDetails(String qty, String name, String cost, String total) {
                TextView itemQty = (TextView) mView.findViewById(R.id.textViewItemQty);
                TextView itemName = (TextView) mView.findViewById(R.id.textViewItemName);
                TextView itemCost = (TextView) mView.findViewById(R.id.textViewItemCost);
                TextView itemTotal = (TextView) mView.findViewById(R.id.textViewTotalAmnt);

                itemQty.setText(qty);
                itemName.setText(name);
                itemCost.setText(cost);
                itemTotal.setText(total);
            }

            public void setItemClickListener(ItemClickListener itemClickListener) {
                this.itemClickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                itemClickListener.onClick(view, getAdapterPosition(), false);
            }
        }
    }
}