package com.example.sportial;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sportial.Fragments.ProfilePageFragment;
import com.example.sportial.Fragments.SearchPageFragment;
import com.example.sportial.UI.ChatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class sportialActivity extends AppCompatActivity {


    private static final int MENU_ITEM_HOME = R.id.navigation_home;
    private static final int MENU_ITEM_PROFILE = R.id.navigation_profile;
    private static final int MENU_ITEM_SEARCH = R.id.navigation_search;
    private static final int MENU_ITEM_CHATS = R.id.navigation_chats;




    BottomNavigationView socialMediaNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sportial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sportial_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        socialMediaNav = findViewById(R.id.sportial_menu);



        socialMediaNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == MENU_ITEM_PROFILE) {
                //change to profile fragment
                Fragment profileFragment = new ProfilePageFragment();
                FragmentManager profileFragmentManager = getSupportFragmentManager();
                profileFragmentManager.beginTransaction().replace(R.id.sportial_fragment_container, profileFragment).commit();
                return true;
            }
            if (item.getItemId() == MENU_ITEM_SEARCH) {
                //change to profile fragment
                Fragment searchPageFragment = new SearchPageFragment();
                FragmentManager profileFragmentManager = getSupportFragmentManager();
                profileFragmentManager.beginTransaction().replace(R.id.sportial_fragment_container, searchPageFragment).commit();
                return true;
            }
            if (item.getItemId() == MENU_ITEM_CHATS) {
                //change to profile fragment
                Intent intent = new Intent(sportialActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });






    }
}