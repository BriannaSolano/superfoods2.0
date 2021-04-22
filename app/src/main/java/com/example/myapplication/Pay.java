package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Config.Config;
import com.example.myapplication.ViewHolder.CustomerHomeScreen;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;


public class Pay extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ImageButton cash;
    private TextView totalshow;
    private String total;
    private String phone;


    //Shared Preference
    private SharedPreferences cartPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone;";
    private static final String KEY_FINAL_PRICE = "totalprice";


    //PayPal

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    ImageButton btnPay;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //start
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        //shared Preference
        cartPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        phone = cartPreferences.getString(KEY_PHONE,null);
        total = cartPreferences.getString(KEY_FINAL_PRICE,null);
        SharedPreferences.Editor editor = cartPreferences.edit();
        editor.commit();

        totalshow = findViewById(R.id.textView29);
        totalshow.setText(total);

        cash = findViewById(R.id.imageButton5);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFirebaseData(phone);
                Toast.makeText(Pay.this, "Order Placed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pay.this, CustomerHomeScreen.class));
            }
        });


        btnPay = findViewById(R.id.imageButton4);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment(total);
            }
        });




        }

    private void processPayment(String total) {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(total)),"USD", "Payment for SuperFoods Order",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        copyFirebaseData(phone);
                        startActivity(new Intent(this, PaymentDetails.class).putExtra("Payment", paymentDetails)
                                .putExtra("PaymentAmount", total));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyFirebaseData(String phone) {
        DatabaseReference cartinformation = FirebaseDatabase.getInstance().getReference().child("Cart").child(phone);
        DatabaseReference menu = FirebaseDatabase.getInstance().getReference().child("Food");
        DatabaseReference inventory = FirebaseDatabase.getInstance().getReference().child("Inventory");
        final DatabaseReference ordermade = FirebaseDatabase.getInstance().getReference().child("Order");

        cartinformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot questionCode : dataSnapshot.getChildren()) {
                    String foodCode = questionCode.getKey();
                    String ingredients = questionCode.child("EliminateIngredients").getValue(String.class);
                    String Foodname = questionCode.child("FoodName").getValue(String.class);
                    String amount = questionCode.child("Quantity").getValue(String.class);
                    String price = questionCode.child("TotalFoodPrice").getValue(String.class);
                    ordermade.child(phone).child(foodCode).child("EliminateIngredients").setValue(ingredients);
                    ordermade.child(phone).child(foodCode).child("FoodName").setValue(Foodname);
                    ordermade.child(phone).child(foodCode).child("Quantity").setValue(amount);
                    ordermade.child(phone).child(foodCode).child("TotalFoodPrice").setValue(price);

                    //Subtract ingredients from inventory
                    ArrayList<String> foodIngredients = new ArrayList<String>();
                    ArrayList<String> eliminatedIngredients = new ArrayList<String>();

                    menu.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                            String description;
                            for(MutableData item : currentData.getChildren()) {
                                Map<String, Object> map = (Map<String, Object>) item.getValue();
                                String id = String.valueOf(item.getKey());
                                Log.d("IDFOUND", id);
                                if(id.equals(foodCode)) {
                                    description = String.valueOf(map.get("Description")).toLowerCase();

                                    String myingredients = ingredients.toLowerCase();
                                    foodIngredients.addAll(Arrays.asList(description.split(" / ")));
                                    eliminatedIngredients.addAll(Arrays.asList(myingredients.split(",")));
                                    Log.d("DESCRIPTION", description);
                                    Log.d("ELIMINATEDINGREDIENTS", eliminatedIngredients.toString());
                                    foodIngredients.removeIf(s -> eliminatedIngredients.contains(s));
                                    Log.d("FOODINGREDIENTS", foodIngredients.toString());
                                    inventory.runTransaction(new Transaction.Handler() {
                                        @NonNull
                                        @Override
                                        public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                            for(MutableData item : currentData.getChildren()) {
                                                Map<String, Object> map = (Map<String, Object>) item.getValue();
                                                String name = String.valueOf(map.get("name")).toLowerCase();
                                                Log.d("ORDER", name);
                                                if(foodIngredients.contains(name)) {
                                                    long count = (long) map.get("count");
                                                    count -= Integer.parseInt(amount);
                                                    map.replace("count", count);
                                                    item.setValue(map);
                                                }
                                            }
                                            return Transaction.success(currentData);
                                        }

                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                            return;
                                        }
                                    });
                                    break;
                                }

                            }
                            return Transaction.abort();
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                        }
                    });

                }
                ordermade.child(phone).child("OrderStatus").setValue("Order Placed");
                cartinformation.removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}