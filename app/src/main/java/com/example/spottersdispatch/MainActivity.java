package com.example.spottersdispatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity  {

    BottomSheetDialog dialog ;
    private int selectedTab  = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout home_lay = findViewById(R.id.home_lay);
        LinearLayout setting_lay = findViewById(R.id.setting_lay);

        TextView home_txt = findViewById(R.id.hometxt);
        TextView setting_txt = findViewById(R.id.setting_txt);

        ImageView setting_img = findViewById(R.id.setting_img);
        ImageView home_img = findViewById(R.id.home_img);
        dialog = new BottomSheetDialog(this);

        home_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 1){

                    setting_txt.setVisibility(View.GONE);

                    setting_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    home_txt.setVisibility(View.VISIBLE);
                    home_lay.setBackgroundResource(R.drawable.nav_back);

                    ScaleAnimation animation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    home_lay.startAnimation(animation);

                    selectedTab = 1;
                }


            }
        });

        setting_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 3){

                    home_txt.setVisibility(View.GONE);

                    home_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    setting_txt.setVisibility(View.VISIBLE);
                    setting_lay.setBackgroundResource(R.drawable.nav_back);

                    ScaleAnimation animation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    setting_lay.startAnimation(animation);

                    selectedTab = 3;

                }

                createdialog();

            }
        });




        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void createdialog() {
        View view = getLayoutInflater().inflate(R.layout.settings_dialog,null,false);
        dialog.setContentView(view);
        dialog.show();
    }
}