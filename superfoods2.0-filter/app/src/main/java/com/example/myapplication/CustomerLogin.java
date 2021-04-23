package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ViewHolder.CustomerHomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerLogin extends AppCompatActivity {

    private SharedPreferences loginPreferences;

    private EditText Email;
    private EditText Password;
    private EditText Phone;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;


    //Keys for Shared Prefrence
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_CHECKBOX = "checkbox";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone;";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersign_in);

        Email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Password = (EditText) findViewById(R.id.editTextTextPassword);
        Phone = (EditText) findViewById(R.id.editTextPhone);
        Info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.employeebutton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        //Shared Preferences
        loginPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        Button btn4 = (Button)findViewById(R.id.button2);

        checkSharedPreferences();

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLogin.this, CustomerRegister.class));
            }
        });

        if(user != null){
            finish();
            startActivity(new Intent(CustomerLogin.this, CustomerHomeScreen.class));
        }

        Login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = loginPreferences.edit();
                if(checkBox.isChecked()){
                    editor.putString(KEY_CHECKBOX,"True");
                    editor.apply();
                    //save email
                    editor.putString(KEY_EMAIL,Email.getText().toString());
                    editor.apply();
                    //save password
                    editor.putString(KEY_PASSWORD,Password.getText().toString());
                    editor.apply();
                }
                else
                {
                    editor.putString(KEY_CHECKBOX,"False");
                    editor.apply();

                    editor.putString(KEY_EMAIL,"");
                    editor.apply();

                    editor.putString(KEY_PASSWORD,"");
                    editor.apply();
                }
                //save phone
                editor.putString(KEY_PHONE,Phone.getText().toString());
                editor.apply();
                validate(Email.getText().toString(), Password.getText().toString());
            }
        }));



    }

    private void validate (String Email,String Password){

        progressDialog.setMessage("Login in Progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CustomerLogin.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CustomerLogin.this, CustomerHomeScreen.class));
                }
                else
                {
                    Toast.makeText(CustomerLogin.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0)
                    {
                        Login.setEnabled(false);
                    }
                }
            }
        });

    }

    private void checkSharedPreferences(){
        String checkbox = loginPreferences.getString(KEY_CHECKBOX,"False");
        String email = loginPreferences.getString(KEY_EMAIL,"");
        String password = loginPreferences.getString(KEY_PASSWORD,"");
        String phone = loginPreferences.getString(KEY_PHONE,"");

        Email.setText(email);
        Password.setText(password);
        Phone.setText(phone);

        if(checkbox.equals("True"))
        {
            checkBox.setChecked(true);
        }
        else
        {
            checkBox.setChecked(false);
        }

    }





}