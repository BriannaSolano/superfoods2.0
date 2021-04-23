package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerRegister extends AppCompatActivity {

    private EditText userBirthdayDay,userBirthdayMonth,userBirthdayYear,userPhone,userName,customerCode,empolyeeCode, userPassword, userEmail;
    //customerCode --> Customer = 1, Not a Customer = 0
    //Employee Code
    //Customer = 0
    //Waiter = 1
    //Drone Delivery = 2
    //Manager = 3
    //Chef = 4
    private Button regButton;
    private FirebaseAuth  firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customermen);
        setupUIViews();

        //Start the Firebase Authenticator
        firebaseAuth = FirebaseAuth.getInstance();

        //Start Firebase to enter data
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userCode = database.getReference("User");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Storing Data into Firebase
                userCode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.child(userPhone.getText().toString()).exists())
                        {
                            Toast.makeText(CustomerRegister.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userInfo user = new userInfo (userName.getText().toString(),userBirthdayMonth.getText().toString(),userBirthdayDay.getText().toString(),
                                    userBirthdayYear.getText().toString(),"1","0");
                            userCode.child(userPhone.getText().toString()).setValue(user);
                            Toast.makeText(CustomerRegister.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //Store it in Fire Base Authentication
                if (validate()){
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(CustomerRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CustomerRegister.this, CustomerLogin.class));
                            }
                            else
                            {
                                Toast.makeText(CustomerRegister.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }

    private void setupUIViews(){
        userEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        userPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        regButton = (Button) findViewById(R.id.button3);
        userBirthdayDay = (EditText) findViewById(R.id.BirthdayDay);
        userBirthdayMonth=(EditText) findViewById(R.id.BirthdayMonth);
        userBirthdayYear=(EditText) findViewById(R.id.birthdayYear);
        userPhone=(EditText) findViewById(R.id.PhoneNumber);
        userName=(EditText) findViewById(R.id.userName);
    }

    private Boolean validate(){
        Boolean result = false;

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return result;

    }
}