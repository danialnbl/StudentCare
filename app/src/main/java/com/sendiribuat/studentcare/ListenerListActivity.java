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

public class ListenerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private ListenerListAdapter listenerListAdapter;
    private Button viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        viewBtn = (Button) findViewById(R.id.ViewListener);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListenerListActivity.this,tellerListenerListActivity.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.volunteerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Listener> liOptions =
                new FirebaseRecyclerOptions.Builder<Listener>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Listener"), Listener.class)
                        .build();

        listenerListAdapter = new ListenerListAdapter(liOptions);
        recyclerView.setAdapter(listenerListAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        listenerListAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        listenerListAdapter.stopListening();
    }
}