package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DailyPlansActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView BreathMeditationCV, MusicMeditationCV, WorkBreakCV;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plans);

        BreathMeditationCV = findViewById(R.id.BreathMeditationCard);
        BreathMeditationCV.setOnClickListener(this);

        MusicMeditationCV = findViewById(R.id.SoundMeditationCard);
        MusicMeditationCV.setOnClickListener(this);

        WorkBreakCV = findViewById(R.id.workBreakCard);
        WorkBreakCV.setOnClickListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.daily_plan);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.daily_plan):
                        return true;
                    case (R.id.session):
                        startActivity(new Intent(DailyPlansActivity.this,BookSessionActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BreathMeditationCard:
                startActivity(new Intent(this, BreathMeditationActivity.class));
                break;
            case R.id.SoundMeditationCard:
                startActivity(new Intent(this,SoundMeditationActivity.class));
                break;
            case R.id.workBreakCard:
                startActivity(new Intent(this,WorkBreakActivity.class));
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
                        startActivity(new Intent(DailyPlansActivity.this,WelcomeActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        alert(DailyPlansActivity.this);
    }
}