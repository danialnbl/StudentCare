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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class weeklyAgendaAdapter extends FirebaseRecyclerAdapter<StudentRequestModel,weeklyAgendaAdapter.WAHolder>
{
    public weeklyAgendaAdapter(@NonNull FirebaseRecyclerOptions<StudentRequestModel> waOption) {
        super(waOption);
    }

    @Override
    protected void onBindViewHolder(@NonNull WAHolder holder, int position, @NonNull StudentRequestModel model) {
        holder.fullName.setText(model.getFullName());
        holder.age.setText(model.getAge());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.userId.setText(model.getUserid());
        holder.email.setText(model.getEmail());
    }

    @NonNull
    @Override
    public WAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetinglist,parent,false);
        return new WAHolder(view);
    }

    class WAHolder extends RecyclerView.ViewHolder
    {
        TextView age,date,fullName,time,userId,email;
        public WAHolder(@NonNull View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.vFullName);
            age = (TextView) itemView.findViewById(R.id.vAge);
            date = (TextView) itemView.findViewById(R.id.vDate);
            time = (TextView) itemView.findViewById(R.id.vTime);
            userId = (TextView) itemView.findViewById(R.id.userId);
            email = (TextView) itemView.findViewById(R.id.email);

            itemView.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String counselorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("CounselorsSession")
                            .child(counselorID)
                            .child("Students")
                            .child(userId.getText().toString());
                    requestRef.removeValue();
                }
            });
        }

    }
}
