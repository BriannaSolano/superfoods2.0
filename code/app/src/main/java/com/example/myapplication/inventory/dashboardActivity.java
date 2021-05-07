package com.example.myapplication.inventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ManagerHomePage;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class dashboardActivity extends AppCompatActivity implements View.OnClickListener  {
    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;
    Button toast;

    private CardView addItems;
    private CardView viewInventory;
    private CardView orderFromSupplier;
    private RecyclerView stockAlerts;

    private DatabaseReference inventoryRef;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebasenameview = findViewById(R.id.firebasename);
        inventoryRef = FirebaseDatabase.getInstance().getReference("Inventory");

        addItems = (CardView)findViewById(R.id.addItems);
        viewInventory = (CardView) findViewById(R.id.viewInventory);
        orderFromSupplier = (CardView) findViewById(R.id.orderFromSupplier);
        stockAlerts = (RecyclerView) findViewById(R.id.recyclerViewStockAlerts);
        stockAlerts.setLayoutManager(new LinearLayoutManager(this));
        stockAlerts.setHasFixedSize(false);

        addItems.setOnClickListener(this);
        viewInventory.setOnClickListener(this);
        orderFromSupplier.setOnClickListener(this);

        inventoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = new ArrayList<String>();
                for(DataSnapshot item : snapshot.getChildren()) {
                    try {
                        Map<String, Object> map = (Map<String, Object>) item.getValue();
                        int count = Integer.parseInt(String.valueOf(map.get("count")));
                        int min = Integer.parseInt(String.valueOf(map.get("min")));
                        String name = String.valueOf(map.get("name"));
                        if(count < min)  {
                            data.add(name);
                            Log.d("ADDING", name);
                        }
                    } catch (Exception e) {
                    }
                }
                StockAdapter adapter = new StockAdapter(data);
                stockAlerts.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.addItems : i = new Intent(this,addItemActivity.class); startActivity(i); break;
            case R.id.viewInventory : i = new Intent(this,viewInventoryActivity.class);startActivity(i); break;
            case R.id.orderFromSupplier : i = new Intent(this,orderFromSupplierActivity.class);startActivity(i); break;
            default: break;
        }
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(dashboardActivity.this, MainActivity.class));
        Toast.makeText(dashboardActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
    }

    public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder> {

        private ArrayList<String> mData;

        public StockAdapter(ArrayList<String> datas) {
            mData = datas;
        }

        @NonNull
        @Override
        public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_stockholder_layout, parent, false);
            return new StockHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull StockHolder holder, int position) {
            String name = mData.get(position);
            holder.setDetails(name);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class StockHolder extends RecyclerView.ViewHolder {
            View mView;
            ItemClickListener itemClickListener;

            public StockHolder(View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void setDetails(String name) {
                TextView itemName = (TextView) mView.findViewById(R.id.textViewStockItemName);
                itemName.setText(name);
            }

        }
    }
}
