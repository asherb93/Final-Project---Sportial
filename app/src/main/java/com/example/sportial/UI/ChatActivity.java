package com.example.sportial.UI;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.cometchat.chat.models.User;
import com.cometchat.chat.core.CometChat;

import com.example.sportial.R;
import com.example.sportial.sportialActivity;
import com.example.sportial.Data.MyCallback;


import java.util.List;
import java.util.ArrayList;

import retrofit2.http.Tag;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ReadAndWriteSnippets";
    private Button btnBack;
    private UsersConfiguration usersConfiguration = new UsersConfiguration();
    private CometChatUsersWithMessages usersWithMessages;
    private UsersRequest.UsersRequestBuilder usersRequest = new UsersRequest.UsersRequestBuilder();

    List<String> uids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        btnBack = findViewById(R.id.button_back);
        usersWithMessages = findViewById(R.id.users_with_messages);
        User user = CometChat.getLoggedInUser();

        usersRequest.setRole(user.getRole());
        usersConfiguration.setUsersRequestBuilder(usersRequest);
        usersWithMessages.setUsersConfiguration(usersConfiguration);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChatActivity.this, sportialActivity.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

}