package com.sendiribuat.studentcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListenerListAdapter extends FirebaseRecyclerAdapter<Listener,ListenerListAdapter.LiHolder> {

    public ListenerListAdapter(@NonNull FirebaseRecyclerOptions<Listener> liOptions) {
        super(liOptions);
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
    public LiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteerlist,parent,false);
        return new LiHolder(view);
    }

    class LiHolder extends RecyclerView.ViewHolder
    {
        TextView age,phoneNumber,fullName,aboutMe,userId,email;
        String fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr;
        String tellerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String tellerName,tellerEmail,tellerPhone,tellerAge,tellerType;

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
                            .child("Listener")
                            .child(userId.getText().toString());
                    requestRef.removeValue();

                    String userIdStr = userId.getText().toString();
                    fullNameStr = fullName.getText().toString();
                    ageStr = age.getText().toString();
                    phoneStr = phoneNumber.getText().toString();
                    emailStr = email.getText().toString();
                    aboutMeStr = aboutMe.getText().toString();

                    FirebaseDatabase.getInstance().getReference().child("Users").child(tellerID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tellerName = snapshot.child("fullName").getValue().toString();
                            tellerEmail = snapshot.child("email").getValue().toString();
                            tellerPhone = snapshot.child("phone").getValue().toString();
                            tellerAge = snapshot.child("age").getValue().toString();
                            tellerType = snapshot.child("userType").getValue().toString();

                            Student student = new Student(tellerName,tellerAge,tellerEmail,tellerPhone,tellerType);

                            FirebaseDatabase.getInstance().getReference("ListenerSession")
                                    .child(userIdStr)
                                    .child(tellerID)
                                    .setValue(student);

                            Listener listener = new Listener(userIdStr,fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr);

                            FirebaseDatabase.getInstance().getReference("TellerSession")
                                    .child(tellerID)
                                    .child(userIdStr)
                                    .setValue(listener);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Listener listener = new Listener(userIdStr,fullNameStr,ageStr,phoneStr,emailStr,aboutMeStr);

                    FirebaseDatabase.getInstance().getReference("ListenerSession")
                            .child(userIdStr)
                            .setValue(listener);

                }
            });
        }
    }
}
