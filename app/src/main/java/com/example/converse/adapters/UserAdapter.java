package com.example.converse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.converse.ChatterActivity;
import com.example.converse.R;
import com.example.converse.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

   ArrayList<Users> list;
   Context context;
   public UserAdapter(Context context,ArrayList<Users> list){
       this.list=list;
       this.context=context;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.sample_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users=list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.user__1_).into(holder.image);
        holder.contactName.setText(users.getName());
        FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + users.getUserId()).
                orderByChild("timestamp").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        holder.lastMessage.setText(dataSnapshot.child("message").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatterActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("userProfilePic",users.getProfilePic());
                intent.putExtra("userName",users.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView contactName, lastMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            contactName = itemView.findViewById(R.id.contactName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
