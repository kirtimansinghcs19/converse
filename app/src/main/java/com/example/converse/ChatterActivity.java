package com.example.converse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.converse.adapters.ChatAdapter;
import com.example.converse.databinding.ActivityChatterBinding;
import com.example.converse.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatterActivity extends AppCompatActivity {

    ActivityChatterBinding activityChatterBinding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatterBinding = ActivityChatterBinding.inflate(getLayoutInflater());
        setContentView(activityChatterBinding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();


        final String senderId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userProfilePic = getIntent().getStringExtra("userProfilePic");

        activityChatterBinding.user.setText(userName);
        Picasso.get().load(userProfilePic).placeholder(R.drawable.user__1_).into(activityChatterBinding.profileImage);
        activityChatterBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    ArrayList<MessageModel> messageModels = new ArrayList<>();
    final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
    activityChatterBinding.chatRecycler.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityChatterBinding.chatRecycler.setLayoutManager(layoutManager);

        String senderRoom = senderId + receiveId;
        String receiverRoom = receiveId + senderId;

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
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

        activityChatterBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = activityChatterBinding.enterMessage.getText().toString();
                MessageModel messageModel = new MessageModel(senderId,message);
                messageModel.setTimesTamp(new Date().getTime());
                activityChatterBinding.enterMessage.setText("");
                database.getReference().child("chats").child(senderRoom).push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        database.getReference().child("chats").child(receiverRoom).push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {

                            }
                        });
                    }
                });
            }
        });

    }
}