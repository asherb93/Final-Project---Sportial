package com.example.sportial.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportial.R;
import com.example.sportial.sportialActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePageActivity extends AppCompatActivity {

    private static final int MENU_ITEM_HOME = R.id.navigation_home;
    private static final int MENU_ITEM_PROFILE = R.id.navigation_profile;
    private static final int MENU_ITEM_SEARCH = R.id.navigation_search;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initVews();
        navigationViewListeners();


    }

    private void initVews() {

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

    }


    private void navigationViewListeners() {


        bottomNavigationView.setSelectedItemId(MENU_ITEM_HOME);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == MENU_ITEM_PROFILE) {
                startActivity(new Intent(HomePageActivity.this, sportialActivity.class));
                return true;
            }
            else if (item.getItemId() == MENU_ITEM_SEARCH) {
                //  startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            }
            return false;
        });

    }

}