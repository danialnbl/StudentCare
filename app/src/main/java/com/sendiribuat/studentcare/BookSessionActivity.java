package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private CalendarView calendarView;
    private TextView testText;
    private Spinner mySpinner;
    private Button confirmButton;
    private String Date, userFullNameDB, userAgeDB, time;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_session);

        calendarView = findViewById(R.id.calendarView);
        testText = findViewById(R.id.testText);
        mySpinner = findViewById(R.id.spinner);
        confirmButton = findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(this);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BookSessionActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date =  i2 + "/" + (i1 + 1) + "/" +i;
                testText.setText(date);
                Date = date;
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.session);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.session):
                        return true;
                    case (R.id.daily_plan):
                        startActivity(new Intent(BookSessionActivity.this,DailyPlansActivity.class));
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
            case R.id.confirmBtn:



                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                String userID = user.getUid();
                reference.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userFullNameDB = snapshot.child("fullName").getValue().toString();
                        userAgeDB = snapshot.child("age").getValue().toString();
                        time = mySpinner.getSelectedItem().toString();
                        session = new Session(userFullNameDB,userAgeDB,Date,time);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                FirebaseDatabase.getInstance().getReference("Request")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(session).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookSessionActivity.this, "Berjaya", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(BookSessionActivity.this, "Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }
}