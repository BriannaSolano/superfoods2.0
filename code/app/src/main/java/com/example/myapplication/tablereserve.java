package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tablereserve extends AppCompatActivity {

    //Grey//0 = unoccupied
    //Red//1 = occupied
    //Orange//2 = Needs to be cleaned
    //Blue//3 = Selected

    //Shared Preference
    private SharedPreferences cartPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone;";
    ImageButton table1,table2,table3,table4,table5,table6,table7,table8,table9,table10,table;
    Button finaltableselect;
    int reserve1table = 0;
    int clicks1 = 0, clicks2 = 0, clicks3 = 0, clicks4 = 0, clicks5 = 0, clicks6 = 0, clicks7 = 0, clicks8 = 0, clicks9 = 0, clicks10 = 0;
    int tablereserve = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablereserve);

        cartPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String phone = cartPreferences.getString(KEY_PHONE,null);
        SharedPreferences.Editor editor = cartPreferences.edit();
        editor.commit();

        //Table buttones
        table1 = (ImageButton) findViewById(R.id.imageButton6);
        table2 = (ImageButton) findViewById(R.id.imageButton7);
        table3 = (ImageButton) findViewById(R.id.imageButton8);
        table4 = (ImageButton) findViewById(R.id.imageButton9);
        table5 = (ImageButton) findViewById(R.id.imageButton10);
        table6 = (ImageButton) findViewById(R.id.imageButton11);
        table7 = (ImageButton) findViewById(R.id.imageButton12);
        table8 = (ImageButton) findViewById(R.id.imageButton13);
        table9 = (ImageButton) findViewById(R.id.imageButton14);
        table10 = (ImageButton) findViewById(R.id.imageButton15);
        finaltableselect = (Button) findViewById(R.id.selectTable);

        DatabaseReference tableinformation = FirebaseDatabase.getInstance().getReference().child("Table");
        tableinformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("1")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table1.setBackgroundColor(getResources().getColor(R.color.red));
                            table1.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table1.setBackgroundColor(getResources().getColor(R.color.red));
                            table1.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table1.setBackgroundColor(getResources().getColor(R.color.white));
                            table1.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("2")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table2.setBackgroundColor(getResources().getColor(R.color.red));
                            table2.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table2.setBackgroundColor(getResources().getColor(R.color.red));
                            table2.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table2.setBackgroundColor(getResources().getColor(R.color.white));
                            table2.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("3")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table3.setBackgroundColor(getResources().getColor(R.color.red));
                            table3.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table3.setBackgroundColor(getResources().getColor(R.color.red));
                            table3.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table3.setBackgroundColor(getResources().getColor(R.color.white));
                            table3.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("4")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table4.setBackgroundColor(getResources().getColor(R.color.red));
                            table4.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table4.setBackgroundColor(getResources().getColor(R.color.red));
                            table4.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table4.setBackgroundColor(getResources().getColor(R.color.white));
                            table4.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("5")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table5.setBackgroundColor(getResources().getColor(R.color.red));
                            table5.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table5.setBackgroundColor(getResources().getColor(R.color.red));
                            table5.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table5.setBackgroundColor(getResources().getColor(R.color.white));
                            table5.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("6")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table6.setBackgroundColor(getResources().getColor(R.color.red));
                            table6.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table6.setBackgroundColor(getResources().getColor(R.color.red));
                            table6.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table6.setBackgroundColor(getResources().getColor(R.color.white));
                            table6.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("7")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table7.setBackgroundColor(getResources().getColor(R.color.red));
                            table7.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table7.setBackgroundColor(getResources().getColor(R.color.red));
                            table7.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table7.setBackgroundColor(getResources().getColor(R.color.white));
                            table7.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("8")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table8.setBackgroundColor(getResources().getColor(R.color.red));
                            table8.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table8.setBackgroundColor(getResources().getColor(R.color.red));
                            table8.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table8.setBackgroundColor(getResources().getColor(R.color.white));
                            table8.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("9")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table9.setBackgroundColor(getResources().getColor(R.color.red));
                            table9.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table9.setBackgroundColor(getResources().getColor(R.color.red));
                            table9.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table9.setBackgroundColor(getResources().getColor(R.color.white));
                            table9.setEnabled(true);
                        }
                    }
                    if(ds.child("Tablenum").getValue().toString().equalsIgnoreCase("10")) {
                        if (ds.child("Status").getValue().toString().equalsIgnoreCase("1")) {
                            table10.setBackgroundColor(getResources().getColor(R.color.red));
                            table10.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("2")) {
                            table10.setBackgroundColor(getResources().getColor(R.color.red));
                            table10.setEnabled(false);
                        } else if (ds.child("Status").getValue().toString().equalsIgnoreCase("0")) {
                            table10.setBackgroundColor(getResources().getColor(R.color.white));
                            table10.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tableinformation2 = FirebaseDatabase.getInstance().getReference().child("Table");
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks1 == 1) {
                    tableinformation2.child("1").child("Status").setValue("0");
                    reserve1table = 0;
                    tablereserve = 0;
                    table1.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks1 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                }
                else {
                    clicks1 = clicks1 + 1;
                    if (clicks1 == 1) {
                        tableinformation2.child("1").child("Status").setValue("3");
                        reserve1table = 1;
                        tablereserve = 1;
                        table1.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                    }
                }
            }

        });

        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks2 == 1)
                {
                    tableinformation2.child("2").child("Status").setValue("0");
                    reserve1table = 0;
                    table2.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks2 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks2 = clicks2 + 1;
                    if (clicks2 == 1) {
                        tableinformation2.child("2").child("Status").setValue("3");
                        reserve1table = 1;
                        table2.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 2;
                    }
                }
            }

        });

        table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks3 == 1)
                {
                    tableinformation2.child("3").child("Status").setValue("0");
                    reserve1table = 0;
                    table3.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks3 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks3 = clicks3 + 1;
                    if (clicks3 == 1) {
                        tableinformation2.child("3").child("Status").setValue("3");
                        reserve1table = 1;
                        table3.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 3;
                    }
                }
            }

        });



        table4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks4 == 1)
                {
                    tableinformation2.child("4").child("Status").setValue("0");
                    reserve1table = 0;
                    table4.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks4 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks4 = clicks4 + 1;
                    if (clicks4 == 1) {
                        tableinformation2.child("4").child("Status").setValue("3");
                        reserve1table = 1;
                        table4.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 4;
                    }
                }
            }

        });



        table5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks5 == 1)
                {
                    tableinformation2.child("5").child("Status").setValue("0");
                    reserve1table = 0;
                    table5.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks5 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks5 = clicks5 + 1;
                    if (clicks5 == 1) {
                        tableinformation2.child("5").child("Status").setValue("3");
                        reserve1table = 1;
                        table5.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 5;
                    }
                }
            }

        });



        table6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1)
                {
                    tableinformation2.child("6").child("Status").setValue("0");
                    reserve1table = 0;
                    table6.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks6 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks6 = clicks6 + 1;
                    if (clicks6 == 1) {
                        tableinformation2.child("6").child("Status").setValue("3");
                        reserve1table = 1;
                        table6.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 6;

                    }
                }
            }

        });



        table7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks7 == 1)
                {
                    tableinformation2.child("7").child("Status").setValue("0");
                    reserve1table = 0;
                    table7.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks7 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks7 = clicks7 + 1;
                    if (clicks7 == 1) {
                        tableinformation2.child("7").child("Status").setValue("3");
                        reserve1table = 1;
                        table7.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table8.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 7;
                    }
                }
            }

        });



        table8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks8 == 1)
                {
                    tableinformation2.child("8").child("Status").setValue("0");
                    reserve1table = 0;
                    table8.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks8 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks8 = clicks8 + 1;
                    if (clicks8 == 1) {
                        tableinformation2.child("8").child("Status").setValue("3");
                        reserve1table = 1;
                        table8.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table9.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 8;
                    }
                }
            }

        });


        table9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks9 == 1)
                {
                    tableinformation2.child("9").child("Status").setValue("0");
                    reserve1table = 0;
                    table9.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks9 = 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks9 = clicks9 + 1;
                    if (clicks9 == 1) {
                        tableinformation2.child("9").child("Status").setValue("3");
                        reserve1table = 1;
                        table9.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table8.setEnabled(false);
                        table10.setEnabled(false);
                        tablereserve = 9;
                    }
                }
            }

        });


        table10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reserve1table == 1 && clicks10 == 1)
                {
                    tableinformation2.child("10").child("Status").setValue("0");
                    reserve1table = 0;
                    table10.setBackgroundColor(getResources().getColor(R.color.white));
                    clicks10= 0;
                    table1.setEnabled(true);
                    table2.setEnabled(true);
                    table3.setEnabled(true);
                    table4.setEnabled(true);
                    table5.setEnabled(true);
                    table6.setEnabled(true);
                    table7.setEnabled(true);
                    table8.setEnabled(true);
                    table9.setEnabled(true);
                    table10.setEnabled(true);
                    tablereserve = 0;
                }
                else {
                    clicks10 = clicks10 + 1;
                    if (clicks10 == 1) {
                        tableinformation2.child("10").child("Status").setValue("3");
                        reserve1table = 1;
                        table10.setBackgroundColor(getResources().getColor(R.color.maps_qu_google_blue_500));
                        table1.setEnabled(false);
                        table2.setEnabled(false);
                        table3.setEnabled(false);
                        table4.setEnabled(false);
                        table5.setEnabled(false);
                        table6.setEnabled(false);
                        table7.setEnabled(false);
                        table9.setEnabled(false);
                        table8.setEnabled(false);
                        tablereserve = 10;
                    }
                }
            }

        });

        finaltableselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tablereserveString = String.valueOf(tablereserve);
                if(tablereserve == 0)
                {
                    Toast.makeText(tablereserve.this,"Select a Table", Toast.LENGTH_SHORT);
                }
                else {
                    tableinformation2.child(tablereserveString).child("Reservation ID").setValue(phone);
                    tableinformation2.child(tablereserveString).child("Status").setValue("1");
                    Intent TableReservenIntent = new Intent(tablereserve.this, CategoryList.class);
                    startActivity(TableReservenIntent);
                }
            }
        });


    }
}