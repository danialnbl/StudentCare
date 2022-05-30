package com.sendiribuat.studentcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WorkBreakActivity extends AppCompatActivity {

    private TextView timer;
    private long duration;
    private CountDownTimer coundownlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_break);

        timer = findViewById(R.id.timer);
        duration = TimeUnit.MINUTES.toMillis(5);

        coundownlah = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Your break has ended", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),DailyPlansActivity.class));
            }
        }.start();
    }

    public void alert (Context mContext){
        new AlertDialog.Builder(mContext)
                .setTitle("Cancel Break?")
                .setMessage("Are you sure you want to cancel your work break?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        coundownlah.cancel();
                        startActivity(new Intent(WorkBreakActivity.this,DailyPlansActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        alert(WorkBreakActivity.this);
    }
}