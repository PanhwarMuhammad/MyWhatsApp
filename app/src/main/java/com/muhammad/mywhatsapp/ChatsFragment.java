package com.muhammad.mywhatsapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muhammad.mywhatsapp.Adapters.UsersAdapter;
import com.muhammad.mywhatsapp.Models.Users;
import com.muhammad.mywhatsapp.databinding.FragmentChatsBinding;
import java.util.ArrayList;

public class ChatsFragment extends Fragment implements UsersAdapter.OnItemClickListener {

    private FragmentChatsBinding binding;
    private ArrayList<Users> list = new ArrayList<>();
    private FirebaseDatabase database;
    private UsersAdapter adapter;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        // Initialize database
        database = FirebaseDatabase.getInstance();

        // Initialize adapter and recycler view
        //adapter = new UsersAdapter(list, getContext());
        adapter = new UsersAdapter(list, getContext(), this);
        binding.chatsRecycler.setAdapter(adapter);
        binding.chatsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up database listener
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (users != null) {
                        users.setUserId(dataSnapshot.getKey());
                        list.add(users);
                        Log.d("Firebase", "Added user: " + users.getUserName());

                    }
                }
                Log.d("Firebase", "Total users fetched: " + list.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled
                Log.e("Firebase", "Error fetching users: " + error.getMessage());
            }
        });

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onItemClick(Users user) {
        // Start ChatDetailActivity
        Intent intent = new Intent(getContext(), ChatsDetail.class);
        intent.putExtra("userId", user.getUserId());
        intent.putExtra("userName", user.getUserName());
        intent.putExtra("profilePic", user.getProfilePic());
        startActivity(intent);
    }
}