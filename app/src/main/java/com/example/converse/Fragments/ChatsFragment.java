package com.example.converse.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.converse.adapters.UserAdapter;
import com.example.converse.databinding.FragmentChatsBinding;
import com.example.converse.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    FragmentChatsBinding fragmentChatsBinding;
    ArrayList<Users> list = new ArrayList<Users>();
    FirebaseDatabase firebaseDatabase;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentChatsBinding = FragmentChatsBinding.inflate(inflater, container, false);
        UserAdapter userAdapter = new UserAdapter(getContext(),list);
        fragmentChatsBinding.chatRecyclerView.setAdapter(userAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentChatsBinding.chatRecyclerView.setLayoutManager(layoutManager);
        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users user=dataSnapshot.getValue(Users.class);
                    user.setUserId(dataSnapshot.getKey());
                    list.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return fragmentChatsBinding.getRoot();
    }
}