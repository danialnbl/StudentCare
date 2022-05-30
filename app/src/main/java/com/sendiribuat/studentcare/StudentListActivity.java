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
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView,studentRequestList;
    private StudentListAdapter adapter;
    private StudentRequestAdapter requestAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentRequestList = (RecyclerView) findViewById(R.id.studentRequest);
        studentRequestList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<StudentRequestModel> requestOption =
                new FirebaseRecyclerOptions.Builder<StudentRequestModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Request"), StudentRequestModel.class)
                .build();

        requestAdapter = new StudentRequestAdapter(requestOption);

        studentRequestList.setAdapter(requestAdapter);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Counselors").child(userId).child("Students"), model.class)
                        .build();

        adapter = new StudentListAdapter(options);
        recyclerView.setAdapter(adapter);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.student_list);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.student_list):
                        return true;
                    case (R.id.weeklyAgenda):
                        startActivity(new Intent(StudentListActivity.this,CounselorWeeklyAgendaActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
        requestAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
        requestAdapter.stopListening();
    }

    public void alert (Context mContext){
        new AlertDialog.Builder(mContext)
                .setTitle("Logout?")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(StudentListActivity.this,WelcomeActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        alert(StudentListActivity.this);
    }

}