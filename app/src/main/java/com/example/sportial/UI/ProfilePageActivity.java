package com.example.sportial.UI;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportial.Adapter.PV_RV_Adapter;
import com.example.sportial.Adapter.SC_RV_Adapter;
import com.example.sportial.R;
import com.example.sportial.profilePostsFragment;

import java.util.ArrayList;

public class ProfilePageActivity extends AppCompatActivity {

    ArrayList<postCardModel> postCardArray=new ArrayList<>();

    Button postsFragmentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        postsFragmentBtn = findViewById(R.id.profile_posts_btn);
        postsFragmentBtn.setOnClickListener(v->{
                replaceFragment(new profilePostsFragment());

        });
//        RecyclerView recyclerView = findViewById(R.id.user_posts_RV);
//        setPostCardArray(postCardArray);
//        PV_RV_Adapter adapter = new PV_RV_Adapter(this,postCardArray);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void replaceFragment(Fragment profilePostsFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main,profilePostsFragment);
        fragmentTransaction.commit();
    }



}