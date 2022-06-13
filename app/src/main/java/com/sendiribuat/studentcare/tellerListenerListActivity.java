package com.sendiribuat.studentcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class tellerListenerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private viewListenerAdapter viewListenerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller_listener_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.yourVolunteerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Listener> options =
                new FirebaseRecyclerOptions.Builder<Listener>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TellerSession").child(userId), Listener.class)
                        .build();

        viewListenerAdapter = new viewListenerAdapter(options);
        recyclerView.setAdapter(viewListenerAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        viewListenerAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        viewListenerAdapter.stopListening();
    }
}