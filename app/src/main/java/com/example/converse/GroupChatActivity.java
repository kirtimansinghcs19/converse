package com.example.converse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.converse.adapters.ChatAdapter;
import com.example.converse.databinding.ActivityGroupChatBinding;
import com.example.converse.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {


    ActivityGroupChatBinding activityGroupChatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGroupChatBinding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(activityGroupChatBinding.getRoot());

        getSupportActionBar().hide();
        activityGroupChatBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final String senderId = FirebaseAuth.getInstance().getUid();
        activityGroupChatBinding.user.setText("Group Chat");
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        activityGroupChatBinding.chatRecycler.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityGroupChatBinding.chatRecycler.setLayoutManager(layoutManager);

        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        activityGroupChatBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = activityGroupChatBinding.enterMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId,message);
                model.setTimesTamp(new Date().getTime());
                activityGroupChatBinding.enterMessage.setText("");
                database.getReference().child("Group Chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {

                    }
                });
            }
        });
    }
}