package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class employee_sign_in extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private EditText Phone;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sign_in);

        Email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Password = (EditText) findViewById(R.id.editTextTextPassword);
        Phone = (EditText) findViewById(R.id.EmplyeeSigninNum);
        Info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.employeebutton);

        //Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userCode = database.getReference("User");

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(employee_sign_in.this, employee_sign_in.class));
        }

        Login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userInfo user = snapshot.child(Phone.getText().toString()).getValue(userInfo.class);
                        if (user.getEmployee().equals("0")) {
                            Toast.makeText(employee_sign_in.this, "This Page is only for Employees", Toast.LENGTH_LONG).show();
                        } else {
                            String employeeId = user.getEmployee();
                            validate(Email.getText().toString(), Password.getText().toString(), employeeId);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }));


    }

    private void validate(String Email, String Password, String employeeId) {

        progressDialog.setMessage("Login in Progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && (employeeId.equals("3"))) {
                    progressDialog.dismiss();
                    Toast.makeText(employee_sign_in.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(employee_sign_in.this, ManagerHomePage.class));

                }
                else if (task.isSuccessful() && (employeeId.equals("1"))) {
                    progressDialog.dismiss();
                    Toast.makeText(employee_sign_in.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(employee_sign_in.this, WaiterHomePage.class));

                }
                else if (task.isSuccessful() && (employeeId.equals("2"))) {
                    progressDialog.dismiss();
                    Toast.makeText(employee_sign_in.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(employee_sign_in.this, DroneHomePage.class));

                }
                else if (task.isSuccessful() && (employeeId.equals("4"))) {
                    progressDialog.dismiss();
                    Toast.makeText(employee_sign_in.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(employee_sign_in.this, ChefHomePage.class));
                }
                else {
                    Toast.makeText(employee_sign_in.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
            }
        });

    }
}

