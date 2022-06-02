package com.sendiribuat.studentcare;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class StudentRequestAdapter extends FirebaseRecyclerAdapter<StudentRequestModel,StudentRequestAdapter.studentRequestHolder>
{

    public StudentRequestAdapter(@NonNull FirebaseRecyclerOptions<StudentRequestModel> requestOption) {
        super(requestOption);
    }

    @Override
    protected void onBindViewHolder(@NonNull studentRequestHolder holder, int position, @NonNull StudentRequestModel model) {
        holder.fullName.setText(model.getFullName());
        holder.age.setText(model.getAge());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.userId.setText(model.getUserid());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
    }

    @NonNull
    @Override
    public studentRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentrequestlist,parent,false);
        return new studentRequestHolder(view);
    }

    class studentRequestHolder extends RecyclerView.ViewHolder{
        TextView age,date,fullName,time,userId,email,phone;

        public studentRequestHolder(@NonNull View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.vFullName);
            age = (TextView) itemView.findViewById(R.id.vAge);
            date = (TextView) itemView.findViewById(R.id.vDate);
            time = (TextView) itemView.findViewById(R.id.vTime);
            userId = (TextView) itemView.findViewById(R.id.userId);
            email = (TextView) itemView.findViewById(R.id.email);
            phone = (TextView) itemView.findViewById(R.id.phone);

            itemView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String counselorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Session session = new Session(fullName.getText().toString(),age.getText().toString(),date.getText().toString(),
                            time.getText().toString(),userId.getText().toString(),email.getText().toString(),phone.getText().toString());

                    FirebaseDatabase.getInstance().getReference("CounselorsSession")
                            .child(counselorID).child("Students").child(userId.getText().toString()).setValue(session);

                    FirebaseDatabase.getInstance().getReference("Counselors")
                            .child(counselorID).child("Students").child(userId.getText().toString()).setValue(session);

                    DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("Request")
                            .child(userId.getText().toString());
                    requestRef.removeValue();
                }
            });
        }
    }
}
