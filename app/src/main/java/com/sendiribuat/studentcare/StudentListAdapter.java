package com.sendiribuat.studentcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StudentListAdapter extends FirebaseRecyclerAdapter<model,StudentListAdapter.myviewholder>
{

    public StudentListAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {
        holder.fullName.setText(model.getFullName());
        holder.age.setText(model.getAge());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView fullName,age,email,phone;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.vFullName);
            age = (TextView) itemView.findViewById(R.id.vAge);
            email = (TextView) itemView.findViewById(R.id.vEmail);
            phone = (TextView) itemView.findViewById(R.id.vPhone);
        }
    }
}
