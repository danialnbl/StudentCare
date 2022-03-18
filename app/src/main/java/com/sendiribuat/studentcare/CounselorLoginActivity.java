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

public class CounselorLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView CounselorForgotPassword;
    private EditText ETCounselorEmail, ETCounselorPassword;
    private Button CounselorLgBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID, userTypeDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_login);

        CounselorForgotPassword = (TextView) findViewById(R.id.counselorForgotPassword);
        CounselorForgotPassword.setOnClickListener(this);
        CounselorLgBtn = (Button) findViewById(R.id.counselorLoginButton);
        CounselorLgBtn.setOnClickListener(this);

        ETCounselorEmail = (EditText) findViewById(R.id.counselorLoginEmail);
        ETCounselorPassword = (EditText) findViewById(R.id.counselorLoginPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.counselorLoginButton:
                counselorLogin();
                break;

            case R.id.counselorForgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void counselorLogin() {
        String email = ETCounselorEmail.getText().toString().trim();
        String password = ETCounselorPassword.getText().toString().trim();

        if(email.isEmpty()){
            ETCounselorEmail.setError("Email is required!");
            ETCounselorEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETCounselorEmail.setError("Please enter a valid email!");
            ETCounselorEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            ETCounselorPassword.setError("Password is required!");
            ETCounselorPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            ETCounselorPassword.setError("Min password length is 6 characters!");
            ETCounselorPassword.requestFocus();
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

                            if (userTypeDB.equals("Counselor")){
                                startActivity(new Intent(CounselorLoginActivity.this, StudentListActivity.class));
                            }
                            else{
                                Toast.makeText(CounselorLoginActivity.this,"Please use student login page", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(CounselorLoginActivity.this,"Failed to Login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}