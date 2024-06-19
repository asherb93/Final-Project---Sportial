package com.example.sportial.UI;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;
import com.example.sportial.R;

public class ChatActivity extends AppCompatActivity {
    String appID = "2595125acb2dbc47"; // Replace with your App ID
    String region = "eu"; // Replace with your App Region ("EU" or "US")
    String authKey= "0a834b669662dbbfad2becdf11e24f02a396d6e0"; // Replace with your App ID
    UIKitSettings uiKitSettings = new UIKitSettings.UIKitSettingsBuilder()
            .setRegion(region)
            .setAppId(appID)
            .setAuthKey(authKey)
            .subscribePresenceForAllUsers().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CometChatUIKit.init(this, uiKitSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successString) {
             //   /_Your action after initializing CometChat_/
            }

            @Override
            public void onError(CometChatException e) {}

        });
        CometChatUIKit.login("superhero1", new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Login Successful : " + user.toString());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Login Failed : " + e.getMessage());
            }

        });

    }


}