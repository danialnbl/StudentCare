package com.sendiribuat.studentcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DailyPlansActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView BreathMeditationCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plans);

        BreathMeditationCV = findViewById(R.id.BreathMeditationCard);
        BreathMeditationCV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BreathMeditationCard:
                startActivity(new Intent(this, BreathMeditationActivity.class));
                break;
        }
    }
}