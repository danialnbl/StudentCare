package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText ETLoginEmail, ETLoginPassword;
    private Button logInB;

    TabLayout tabLayout;
    ViewPager viewPager;


    private FirebaseAuth mAuth;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);


        //Tab Tab Tab
        tabLayout.addTab(tabLayout.newTab().setText("Student"));
        tabLayout.addTab(tabLayout.newTab().setText("Counselor"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Initialize
        register = (TextView) findViewById(R.id.studentToRegister);
        register.setOnClickListener(this);
        logInB = (Button) findViewById(R.id.studentLoginButton);
        logInB.setOnClickListener(this);
        ETLoginEmail = (EditText) findViewById(R.id.studentEmail);
        ETLoginPassword = (EditText) findViewById(R.id.studentPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.studentToRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.studentLoginButton:
                    studentLogin();
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
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"Failed to LoginActivity! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}