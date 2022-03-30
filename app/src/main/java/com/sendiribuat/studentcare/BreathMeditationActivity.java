package com.sendiribuat.studentcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.sendiribuat.studentcare.utils.Prefs;

import java.text.MessageFormat;

public class BreathMeditationActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView breatheText, timeTxt, sessionTxt, guideTxt;
    private Button startButton;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_meditation);
        imageView = findViewById(R.id.imageView2);
        breatheText = findViewById(R.id.breathTakenText);
        timeTxt = findViewById(R.id.lastSessionTxt);
        sessionTxt = findViewById(R.id.todayMinutesTxt);
        guideTxt = findViewById(R.id.guideTxt);
        prefs = new Prefs(this);

        startIntroAnimation();

        sessionTxt.setText(MessageFormat.format("{0} min today", prefs.getSessions()));
        breatheText.setText(MessageFormat.format("{0} Breaths",prefs.getBreaths()));
        timeTxt.setText(prefs.getDate());

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });



    }

    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .scale(0,1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .start();
    }

    private void startAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() +1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long l) {
                                //put code to show ticking... 1,2,3...

                            }

                            @Override
                            public void onFinish() {
                                finish();
                                overridePendingTransition( 0, 0);
                                startActivity(new Intent(getApplicationContext(), BreathMeditationActivity.class));
                                overridePendingTransition( 0, 0);
                            }
                        }.start();
                    }
                })
                .start();
    }

}