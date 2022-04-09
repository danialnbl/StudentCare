package com.sendiribuat.studentcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button studentBtn, counselorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        studentBtn = (Button) findViewById(R.id.ToStudentButton);
        studentBtn.setOnClickListener(this);
        counselorBtn = (Button) findViewById(R.id.ToCounselorButton);
        counselorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ToStudentButton:
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0,0);
                break;

            case R.id.ToCounselorButton:
                startActivity(new Intent(this, CounselorLoginActivity.class));
                overridePendingTransition(0,0);
                break;
        }
    }
}