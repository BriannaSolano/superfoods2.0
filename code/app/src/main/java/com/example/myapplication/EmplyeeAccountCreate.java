package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EmplyeeAccountCreate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText userPhone, userName,userPassword, userEmail;
    private Spinner userBirthdayDay, userBirthdayMonth, userBirthdayYear, userEmployeeCode;
    //customerCode --> Customer = 1, Not a Customer = 0
    //Employee Code
    //Customer = 0
    //Waiter = 1
    //Drone Delivery = 2
    //Manager = 3
    //Chef = 4
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    String code;
    boolean newuser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplyee_account_create);
        setupUIViews();

        //Start the Firebase Authenticator
        firebaseAuth = FirebaseAuth.getInstance();

        //Start Firebase to enter data
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userCode = database.getReference("User");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEmployeeCode.getSelectedItem().toString().equalsIgnoreCase("Manager"))
                {
                    code = "3";
                }
                else if (userEmployeeCode.getSelectedItem().toString().equalsIgnoreCase("Waiter"))
                {
                    code = "1";
                }
                else if (userEmployeeCode.getSelectedItem().toString().equalsIgnoreCase("Chef"))
                {
                    code = "4";
                }
                else if (userEmployeeCode.getSelectedItem().toString().equalsIgnoreCase("Drone Delivery"))
                {
                    code = "2";
                }
                //Storing Data into Firebase
                userCode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(userPhone.getText().toString()).exists()) {
                            newuser = true;
                        } else {
                            userInfo user = new userInfo(userName.getText().toString(), userBirthdayMonth.getSelectedItem().toString(), userBirthdayDay.getSelectedItem().toString(),
                                    userBirthdayYear.getSelectedItem().toString(), "0", code);
                            userCode.child(userPhone.getText().toString()).setValue(user);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //Store it in Fire Base Authentication
                if (validate()) {
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmplyeeAccountCreate.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EmplyeeAccountCreate.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }

    private void setupUIViews() {
        userEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        userPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        regButton = (Button) findViewById(R.id.register);
        userPhone = (EditText) findViewById(R.id.PhoneNumber);
        userName = (EditText) findViewById(R.id.userName);
        // Spinner
        userBirthdayDay = (Spinner) findViewById(R.id.birthdayDayspinner);
        userBirthdayMonth = (Spinner) findViewById(R.id.birthdayMonth);
        userBirthdayYear = (Spinner) findViewById(R.id.birthdayYear);
        userEmployeeCode = (Spinner) findViewById(R.id.spinner10);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.birthday_month, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.birthday_day, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.birthday_year, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.employee_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userBirthdayDay.setAdapter(adapter2);
        userBirthdayMonth.setAdapter(adapter1);
        userBirthdayYear.setAdapter(adapter3);
        userEmployeeCode.setAdapter(adapter4);
    }

    private Boolean validate() {
        Boolean result = false;
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String name = userName.getText().toString();
        String phone = userPhone.getText().toString();
        if (password.isEmpty() || email.isEmpty() || name.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if (newuser == true)
        {
            Toast.makeText(this, "Phone number already register", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            result = true;
        }
        return result;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

