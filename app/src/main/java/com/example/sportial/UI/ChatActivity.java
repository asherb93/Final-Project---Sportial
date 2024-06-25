package com.example.sportial.UI;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cometchat.chat.core.UsersRequest;
import com.cometchat.chatuikit.userswithmessages.CometChatUsersWithMessages;
import com.cometchat.chatuikit.users.*;
import com.example.sportial.R;
import com.example.sportial.sportialActivity;
import com.example.sportial.Data.MyCallback;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private Button btnBack;
    private UsersConfiguration usersConfiguration = new UsersConfiguration();
    private CometChatUsersWithMessages usersWithMessages;
    private UsersRequest.UsersRequestBuilder usersRequest = new UsersRequest.UsersRequestBuilder();

    List<String> uids = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference friendDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        btnBack = findViewById(R.id.button_back);

        usersWithMessages = findViewById(R.id.users_with_messages);
        setFriendsList(new MyCallback() {
            @Override
            public void onCallback(List <String> value) {
                usersRequest.setUIDs(value);
                usersConfiguration.setUsersRequestBuilder(usersRequest);
                usersWithMessages.setUsersConfiguration(usersConfiguration);
            }
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChatActivity.this, sportialActivity.class);
            startActivity(intent);
            finish();
        });


    }

    public void setFriendsList(MyCallback myCallback) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/" + firebaseUser.getUid()+ "/FriendRequests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uids.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String status = ds.child("status").getValue(String.class);
                    if(status.equals("friends")){
                        String friendUid = ds.getKey();
                        uids.add(friendUid);
                    }
                }
                myCallback.onCallback(uids);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}