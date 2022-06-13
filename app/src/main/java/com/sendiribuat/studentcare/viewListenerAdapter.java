package com.sendiribuat.studentcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewListenerAdapter extends FirebaseRecyclerAdapter<Listener,viewListenerAdapter.LiHolder> {

    public viewListenerAdapter(@NonNull FirebaseRecyclerOptions<Listener> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiHolder holder, int position, @NonNull Listener model) {
        holder.fullName.setText(model.getFullName());
        holder.age.setText(model.getAge());
        holder.phoneNumber.setText(model.getPhone());
        holder.aboutMe.setText(model.getAbout());
        holder.userId.setText(model.getUserid());
        holder.email.setText(model.getEmail());
    }

    @NonNull
    @Override
    public viewListenerAdapter.LiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteerlist,parent,false);
        return new viewListenerAdapter.LiHolder(view);
    }

    class LiHolder extends RecyclerView.ViewHolder
    {
        TextView age,phoneNumber,fullName,aboutMe,userId,email;
        String fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr;
        String tellerID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        public LiHolder(@NonNull View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.vFullName);
            age = (TextView) itemView.findViewById(R.id.vAge);
            phoneNumber = (TextView) itemView.findViewById(R.id.vPhone);
            aboutMe = (TextView) itemView.findViewById(R.id.vAboutMe);
            userId = (TextView) itemView.findViewById(R.id.userId);
            email = (TextView) itemView.findViewById(R.id.email);

            itemView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference()
                            .child("TellerSession")
                            .child(tellerID)
                            .child(userId.getText().toString());
                    requestRef.removeValue();

                    String userIdStr = userId.getText().toString();
                    fullNameStr = fullName.getText().toString();
                    ageStr = age.getText().toString();
                    phoneStr = phoneNumber.getText().toString();
                    emailStr = email.getText().toString();
                    aboutMeStr = aboutMe.getText().toString();

                }
            });
        }
    }
}
