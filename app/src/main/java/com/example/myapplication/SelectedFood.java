package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectedFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String Text;

    TextView nameofFood,priceofFood,detailofFood;
    ImageView imageofFood;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton Cart;
    String foodId = "";
    private Spinner amount;
    String foodName;
    String foodPrice;
    float Price;
    String totalPrice;
    String calCounttxt;
    int calCount;
    //Ingredients no to include
    Button Quantity;
    TextView quantitySelected;
    String[] productQuantity;
    boolean[] checkedQuanity;
    private Food selectedfood;
    ArrayList<Integer> selectedQuanity = new ArrayList<>();


    // Set Firebase Variables
    FirebaseDatabase database;
    DatabaseReference foods;

    //Set Firebase Variable for Cart
    FirebaseDatabase databaseCart;
    DatabaseReference cart;

    //Shared Preference
    private SharedPreferences cartPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone;";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_food);

        //shared Preference
        cartPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String phone = cartPreferences.getString(KEY_PHONE,null);
        SharedPreferences.Editor editor = cartPreferences.edit();
        editor.commit();

        //amount of food item being added to the cart
        amount = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Number_of_Products, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        amount.setAdapter(adapter);
        amount.setOnItemSelectedListener(this);

        //Ingredients not to include
        Quantity = (Button) findViewById(R.id.ChnageIngridents);
        quantitySelected = (TextView) findViewById(R.id.not_to_include);
        productQuantity = getResources().getStringArray(R.array.Ingredients);
        checkedQuanity = new boolean[productQuantity.length];


        //Starting Firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        //Starting Database for entering cart info
        database = FirebaseDatabase.getInstance();
        cart = database.getReference("Cart");

        //The Cart Button
        Cart = (FloatingActionButton) findViewById(R.id.btnCart);
        nameofFood = (TextView) findViewById(R.id.food_name);
        priceofFood = (TextView) findViewById(R.id.food_price);
        imageofFood = (ImageView) findViewById(R.id.img_food);
        detailofFood = (TextView) findViewById(R.id.food_description);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        if(getIntent() != null)
        {
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty())
        {
            getFoodDiscription(foodId);
        }


        //Ingredients not to include Method
        Quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedFood.this);
                builder.setTitle("Select the Ingredients not to include");
                builder.setMultiChoiceItems(productQuantity, checkedQuanity, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(! selectedQuanity.contains(which)){
                                selectedQuanity.add(which);
                            }
                            else{
                                selectedQuanity.remove(which);
                            }
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item ="";
                        for(int i =0; i < selectedQuanity.size();i++) {
                            item = item + productQuantity[selectedQuanity.get(i)];
                            if (i != selectedQuanity.size() - 1) {
                                item = item + ",";
                            }
                        }
                        quantitySelected.setText(item);
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i < checkedQuanity.length; i++)
                        {
                            checkedQuanity[i] = false;
                            quantitySelected.setText("");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference CartID = database.getReference("Cart");
        final DatabaseReference food = database.getReference("Food");
        //Adding Items to the Cart
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartID.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(phone).exists()) {
                            CartID.child(phone).child(foodId);
                            Price = Float.parseFloat(foodPrice)*Float.parseFloat(Text);
                            totalPrice = Float.toString(Price);
                            calCount = Integer.parseInt(calCounttxt);
                            CartID.child(phone).child(foodId).child("TotalFoodPrice").setValue(totalPrice);
                            CartID.child(phone).child(foodId).child("FoodName").setValue(foodName);
                            CartID.child(phone).child(foodId).child("Quantity").setValue(Text);
                            CartID.child(phone).child(foodId).child("EliminateIngredients").setValue(quantitySelected.getText().toString());
                            CartID.child(phone).child(foodId).child("Calories").setValue(calCounttxt);
                        } else {
                            CartID.child(phone);
                            Price = Float.parseFloat(foodPrice)*Float.parseFloat(Text);
                            totalPrice = Float.toString(Price);
                            calCount = Integer.parseInt(calCounttxt);
                            CartID.child(phone).child(foodId).child("TotalFoodPrice").setValue(totalPrice);
                            CartID.child(phone).child(foodId);
                            CartID.child(phone).child(foodId).child("Quantity").setValue(Text);
                            CartID.child(phone).child(foodId).child("EliminateIngredients").setValue(quantitySelected.getText().toString());
                            CartID.child(phone).child(foodId).child("Calories").setValue(calCounttxt);

                        }
                        Toast.makeText(SelectedFood.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                Toast.makeText(SelectedFood.this,"Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFoodDiscription(String foodId)
    {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedfood = snapshot.getValue(Food.class);
                try {
                    Picasso.with(getBaseContext()).load(selectedfood.getImage()).into(imageofFood);
                } catch(NullPointerException e) {
                    Log.d("NPE", e.toString(), e);
                }
                priceofFood.setText(selectedfood.getPrice());
                nameofFood.setText(selectedfood.getName());
                detailofFood.setText(selectedfood.getDescription());
                foodName = selectedfood.getName().toString();
                foodPrice = selectedfood.getPrice().toString();
                calCounttxt = selectedfood.getCalories().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),Text,Toast.LENGTH_SHORT);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
