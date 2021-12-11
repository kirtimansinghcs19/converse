package com.example.converse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.converse.databinding.ActivityChatterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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


        String senderId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userProfilePic = getIntent().getStringExtra("userProfilePic");

        activityChatterBinding.user.setText(userName);
        Picasso.get().load(userProfilePic).placeholder(R.drawable.user__1_).into(activityChatterBinding.profileImage);
        activityChatterBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}