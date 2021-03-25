package com.example.myapplication.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class viewInventoryActivity extends AppCompatActivity {
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;
    private TextView totalnoofitem, totalnoofsum;
    private int counttotalnoofitem = 0;
    FirebaseRecyclerAdapter<Items, InventoryItemViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        totalnoofitem = findViewById(R.id.totalnoitem);
        totalnoofsum = findViewById(R.id.totalsum);
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Inventory");
        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));


        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    counttotalnoofitem = (int) dataSnapshot.getChildrenCount();
                    totalnoofitem.setText(Integer.toString(counttotalnoofitem));
                } else {
                    totalnoofitem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float sum = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object price = map.get("price");
                        float pValue = Float.parseFloat(String.valueOf(price));
                        sum += pValue;
                        totalnoofsum.setText(String.format("%.2f", sum));
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                return;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = FirebaseDatabase.getInstance().getReference("Inventory").limitToFirst(100);
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, InventoryItemViewHolder>(options) {
            @NonNull
            @Override
            public InventoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);
                return new InventoryItemViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull InventoryItemViewHolder holder, int position, @NonNull Items model) {
               holder.setDetails(model.getCategory(), model.getName(), model.getPrice(), model.getCount() + "/" + model.getMin());
               if(model.getCount() < 0) holder.itemView.findViewById(R.id.viewitemsrellayout).setBackgroundResource(R.drawable.backgroundgradientbrown);
               else if(model.getCount() == 0) holder.itemView.findViewById(R.id.viewitemsrellayout).setBackgroundResource(R.drawable.backgroundgradientred);
               else if(model.getCount() < model.getMin()) holder.itemView.findViewById(R.id.viewitemsrellayout).setBackgroundResource(R.drawable.backgroundgradientgreen);
               else holder.itemView.findViewById(R.id.viewitemsrellayout).setBackgroundResource(R.drawable.backgroundgradientblue);

               holder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                       PopupMenu popup = new PopupMenu(viewInventoryActivity.this, view);
                       popup.inflate(R.menu.selectinventoryitempopupmenu);
                       popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem menuItem) {
                               switch(menuItem.getTitle().toString()) {
                                   case "Edit":
                                       Log.d("TAG","Edit clicked");

                                       break;
                                   case "Delete":
                                       Log.d("TAG","Delete clicked");
                                       Intent intent = new Intent(viewInventoryActivity.this, deleteItemActivity.class);
                                       intent.putExtra("ItemId", String.valueOf(position+1));
                                       startActivity(intent);
                                       finish();
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
        };
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);
        Log.d("TAG", ""+firebaseRecyclerAdapter.getItemCount());
    }

    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        ItemClickListener itemClickListener;
        public InventoryItemViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(this);
        }

        public void setDetails(String itemcategory, String itemname, String itemprice, String itemcountmin){
            TextView item_name = (TextView) mView.findViewById(R.id.viewitemname);
            TextView item_category = (TextView) mView.findViewById(R.id.viewitemcategory);
            TextView item_price = (TextView) mView.findViewById(R.id.viewitemprice);
            TextView item_countmin = (TextView) mView.findViewById(R.id.viewitemcount);

            item_category.setText(itemcategory);
            item_name.setText(itemname);
            item_price.setText(itemprice);
            item_countmin.setText(itemcountmin);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);
        }
    }
}