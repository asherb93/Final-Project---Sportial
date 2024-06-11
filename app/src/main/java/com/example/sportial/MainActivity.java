package com.example.sportial;

import android.content.Intent;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportial.UI.ProfilePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mInputEmail, mInputPassword;
    private FirebaseAuth mAuth;
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

         Button btnSignUp = findViewById(R.id.signup_button);
         Button btnLogin = findViewById(R.id.login_button);
        mInputEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mInputPassword = (EditText) findViewById(R.id.editTextTextPassword);

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
                String email = mInputEmail.getText().toString();
                final String password = mInputPassword.getText().toString();

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
                                    Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            finish();
        }
    }


}