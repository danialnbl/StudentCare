package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button toListenerBtn, toFindListenerBtn;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_menu);

        toListenerBtn = (Button) findViewById(R.id.ToListener);
        toListenerBtn.setOnClickListener(this);
        toFindListenerBtn = (Button) findViewById(R.id.ToFindListener);
        toFindListenerBtn.setOnClickListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.volunteer);

        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.daily_plan):
                        startActivity(new Intent(VolunteerMenuActivity.this,DailyPlansActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case (R.id.session):
                        startActivity(new Intent(VolunteerMenuActivity.this,BookSessionActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case (R.id.volunteer):
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ToListener:
                startActivity(new Intent(this, BeListenerActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.ToFindListener:
                startActivity(new Intent(this, ListenerListActivity.class));
                overridePendingTransition(0,0);
                break;
        }
    }

    public void alert (Context mContext){
        new AlertDialog.Builder(mContext)
                .setTitle("Logout?")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(VolunteerMenuActivity.this,WelcomeActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        alert(VolunteerMenuActivity.this);
    }
}