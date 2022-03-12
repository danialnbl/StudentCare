package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText ETLoginEmail, ETLoginPassword;
    private Button logInB;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID, userTypeDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        logInB = (Button) findViewById(R.id.loginButton);
        logInB.setOnClickListener(this);

        ETLoginEmail = (EditText) findViewById(R.id.loginEmail);
        ETLoginPassword = (EditText) findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.loginButton:
                studentLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void studentLogin() {
        String email = ETLoginEmail.getText().toString().trim();
        String password = ETLoginPassword.getText().toString().trim();

        if(email.isEmpty()){
            ETLoginEmail.setError("Email is required!");
            ETLoginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETLoginEmail.setError("Please enter a valid email!");
            ETLoginEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            ETLoginPassword.setError("Password is required!");
            ETLoginPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            ETLoginPassword.setError("Min password length is 6 characters!");
            ETLoginPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users");
                    userID = user.getUid();

                    reference.child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            userTypeDB = snapshot.child("userType").getValue().toString();

                            if (userTypeDB.equals("Student")){
                                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Please use counselor login page", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Failed to LoginActivity! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}