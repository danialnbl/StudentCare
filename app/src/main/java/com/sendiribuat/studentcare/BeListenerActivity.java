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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BeListenerActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText fullName,age,phone,email,aboutMe;
    private Button submitBtn,stopListenBtn;
    private Listener listener;
    private String fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_listener);

        mAuth = FirebaseAuth.getInstance();

        fullName = (EditText) findViewById(R.id.fullName);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        aboutMe = (EditText) findViewById(R.id.AboutMe);

        submitBtn = (Button) findViewById(R.id.submitListenerBtn);
        submitBtn.setOnClickListener(this);
        stopListenBtn = (Button) findViewById(R.id.CancelBtn);
        stopListenBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitListenerBtn:
                submitFunc();
                break;
            case R.id.CancelBtn:
                cancelFunc();
                break;
        }
    }

    private void cancelFunc(){
        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("Listener")
                .child(userID);
        requestRef.removeValue();
    }

    private void submitFunc(){


        fullNameStr = fullName.getText().toString();
        ageStr = age.getText().toString();
        phoneStr = phone.getText().toString();
        emailStr = email.getText().toString();
        aboutMeStr = aboutMe.getText().toString();

        if(fullNameStr.isEmpty()){
            fullName.setError("Full name is required");
            fullName.requestFocus();
            return;
        }

        if(ageStr.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }

        if(emailStr.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if(aboutMeStr.isEmpty()){
            aboutMe.setError("AboutMe is required");
            aboutMe.requestFocus();
            return;
        }

        if(phoneStr.isEmpty()){
            phone.setError("Phone number is required!");
            phone.requestFocus();
            return;
        }

        if(phoneStr.length() < 10){
            phone.setError("Phone number is invalid!");
            phone.requestFocus();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Listener");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userID)){
                    Toast.makeText(BeListenerActivity.this, "You already a listener, please stop being listener then submit again to update your details", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    listener = new Listener(userID,fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr);

                    FirebaseDatabase.getInstance().getReference("Listener")
                            .child(userID).setValue(listener).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(BeListenerActivity.this, "You have been listed as listener, if there is someone want to talk to you they will contact you very soon :)", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(BeListenerActivity.this, "Session request failed, please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}