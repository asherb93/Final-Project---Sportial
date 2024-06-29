package com.example.sportial.UI;

import static com.example.sportial.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportial.Adapter.SC_RV_Adapter;
import com.example.sportial.FirebaseFunctions;
import com.example.sportial.ImageUploadActivity;
import com.example.sportial.R;
import com.example.sportial.Data.UserModel;
import com.example.sportial.Model.sportCardModel;
import com.example.sportial.UI.LevelActivity;

import java.util.ArrayList;

public class sportChoiceActivity extends AppCompatActivity {


    FirebaseFunctions func = new FirebaseFunctions();
    ArrayList<sportCardModel> sportCards=new ArrayList<>();
    ConstraintLayout layout;
    CardView sportCD;
    ImageView sportIV;
    ImageView previousIV;

    Button continueBtn;

    TextView sportName;

    PorterDuff.Mode colorMode;

    private ViewPropertyAnimator currentAnimator1;
    private ViewPropertyAnimator currentAnimator2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        UserModel user = (UserModel) intent.getSerializableExtra("user");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sport_choose);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.sportRV);
        setUpSportModels(sportCards);
        SC_RV_Adapter adapter = new SC_RV_Adapter(this,sportCards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViews();
        initListeners(user);
    }

    private void findViews() {
        continueBtn=findViewById(id.continueButton);
    }

    private void initListeners(UserModel user) {
            continueBtn.setOnClickListener(v -> {
                try{
                    String sportName = this.sportName.getText().toString();
                    user.setSportType(sportName);
                    Toast.makeText(this, "You choose" + sportName, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this, LevelActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(this, "Please choose a sport", Toast.LENGTH_SHORT).show();
                }

            });

    }


    private void setUpSportModels(ArrayList<sportCardModel> sportCards){
        sportCards.add(new sportCardModel("Football",R.drawable.ic_football,R.drawable.football_background));
        sportCards.add(new sportCardModel("Basketball",R.drawable.ic_basketball,R.drawable.basketball_background));
        sportCards.add(new sportCardModel("Tennis",R.drawable.ic_tennis,R.drawable.tennis_background));
        sportCards.add(new sportCardModel("Volleyball",R.drawable.ic_volleyball,R.drawable.volleyball_background));
        sportCards.add(new sportCardModel("Golf",R.drawable.ic_golf,R.drawable.golf_background));
        sportCards.add(new sportCardModel("Boxing",R.drawable.ic_boxing,R.drawable.boxing_background));
        sportCards.add(new sportCardModel("Swimming",R.drawable.ic_swimming,R.drawable.swim_background));
        sportCards.add(new sportCardModel("Cycling",R.drawable.ic_cycling,R.drawable.cycling_background));
        sportCards.add(new sportCardModel("E-sports", drawable.ic_esport, drawable.esport_background));
    }


    public void enlargeBrightenAddText(View view) {


        if (currentAnimator1 != null||currentAnimator2!=null) {
            currentAnimator1.scaleX(1f).scaleY(1f);
            previousIV.setImageTintMode(PorterDuff.Mode.SRC_OVER);
          //  currentAnimator2.alpha(1f);
        }

        sportCD=(CardView)view;
        currentAnimator1=sportCD.animate().scaleX(1.1f).scaleY(1.1f);
        layout = (ConstraintLayout) (sportCD.getChildAt(0));
        layout.setMinimumHeight(400);
        sportIV = (ImageView) layout.getChildAt(0);
        previousIV=sportIV;
        sportIV.setImageTintMode(null);
        sportName = (TextView)layout.getChildAt(1);


    }
}