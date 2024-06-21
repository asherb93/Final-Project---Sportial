package com.example.sportial;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportial.UI.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;

public class MainActivity extends AppCompatActivity {

    // Constants for SharedPreferences keys
    public static final String PREF_NAME = "MyPreferences";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    private EditText mInputEmail, mInputPassword;
    private FirebaseAuth mAuth;
    private CheckBox rememberMeCB;
    private Button btnSignUp ;
    private Button btnLogin ;
    private String email;
    private  String password;

    SharedPreferences sp;

    String appID = "2595125acb2dbc47"; // Replace with your App ID
    String region = "eu"; // Replace with your App Region ("EU" or "US")
    String authKey= "0a834b669662dbbfad2becdf11e24f02a396d6e0"; // Replace with your App ID
    UIKitSettings uiKitSettings = new UIKitSettings.UIKitSettingsBuilder()
            .setRegion(region)
            .setAppId(appID)
            .setAuthKey(authKey)
            .subscribePresenceForFriends().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LL_profileCreation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CometChatUIKit.init(this, uiKitSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successString) {
            }

            @Override
            public void onError(CometChatException e) {}

        });

        findViews();
        loadEmailPassword();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent object with the target activity class
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);

                // Start the SignupActivity
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = mInputEmail.getText().toString();
                 password = mInputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mInputEmail.setError("Please enter a valid email");
                    Toast.makeText(getApplicationContext(),
                            "Please enter a valid email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mInputEmail.getText().toString()).matches()) {
                    mInputEmail.setError("Please enter a valid email address");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                btnSignUp.setClickable(true);
                                btnLogin.setClickable(true);
                                btnSignUp.setEnabled(true);
                                btnLogin.setEnabled(true);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Sign in failed",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String userId = mAuth.getCurrentUser().getUid();
                                    CometChatUIKit.login(userId, new CometChat.CallbackListener<User>() {
                                        @Override
                                        public void onSuccess(User user) {
                                            Log.d(TAG, "Login Successful : " + user.toString());
                                        }

                                        @Override
                                        public void onError(CometChatException e) {
                                            Log.e(TAG, "Login Failed : " + e.getMessage());
                                        }
                                    });
                                    if(rememberMeCB.isChecked())
                                    {
                                        setEmailPassword();
                                    }
                                    Intent intent = new Intent(MainActivity.this, sportialActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }

    private void findViews() {
         btnSignUp = findViewById(R.id.signup_button);
         btnLogin = findViewById(R.id.login_button);
        mInputEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mInputPassword = (EditText) findViewById(R.id.editTextTextPassword);
        rememberMeCB = (CheckBox) findViewById(R.id.remember_me_checkbox);
    }

    private void loadEmailPassword()
    {
        sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String email = sp.getString(EMAIL_KEY, "");
        String password = sp.getString(PASSWORD_KEY, "");
        if(!email.isEmpty() && !password.isEmpty() ){
            mInputEmail.setText(email);
            mInputPassword.setText(password);
        }
    }

    private void setEmailPassword(){
         sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
        Toast.makeText(this, "Email and password saved", Toast.LENGTH_SHORT).show();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            finish();
        }
    }


}