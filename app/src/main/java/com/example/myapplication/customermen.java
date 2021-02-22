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

public class customermen extends AppCompatActivity {

    private EditText userBirthday, userPassword, userEmail;
    private Button regButton;
    private FirebaseAuth  firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customermen);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(customermen.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(customermen.this,Activity2.class));
                            }
                            else
                            {
                                Toast.makeText(customermen.this, "Registration Failed", Toast.LENGTH_SHORT).show();
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