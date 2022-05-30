package com.sendiribuat.studentcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CounselorWeeklyAgendaActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private weeklyAgendaAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselor_weekly_agenda);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.meetingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<StudentRequestModel> waOption =
                new FirebaseRecyclerOptions.Builder<StudentRequestModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CounselorsSession").child(userId).child("Students"), StudentRequestModel.class)
                        .build();

        requestAdapter = new weeklyAgendaAdapter(waOption);
        recyclerView.setAdapter(requestAdapter);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.weeklyAgenda);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.student_list):
                        startActivity(new Intent(CounselorWeeklyAgendaActivity.this,StudentListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case (R.id.weeklyAgenda):
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        requestAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        requestAdapter.stopListening();
    }

    public void alert (Context mContext){
        new AlertDialog.Builder(mContext)
                .setTitle("Logout?")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(CounselorWeeklyAgendaActivity.this,WelcomeActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        alert(CounselorWeeklyAgendaActivity.this);
    }
}