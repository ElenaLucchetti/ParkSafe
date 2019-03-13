package com.example.parksafe;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.Circle;

import java.util.ArrayList;


public class WriteReview extends AppCompatActivity {

    private FloatingActionButton filterCameraButton, filterAlldayButton, filterChargeButton, filterVerfiedButton, filterShelterButton;
    View.OnClickListener onClickSetTint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        View.OnClickListener onClickFinish = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        final Button skipButton = findViewById(R.id.skip);
        final Button submitButton = findViewById(R.id.submit);
        skipButton.setOnClickListener(onClickFinish);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final LinearLayout buttonPanel = findViewById(R.id.buttonPanel);

        int childCount = buttonPanel.getChildCount();
        int i = 0;

        while (i<childCount){
            buttonPanel.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setActivated(true);
                }
            });
            i++;
        }
        onClickSetTint = new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                ColorStateList selected = ColorStateList.valueOf(Color.parseColor("#FF008577"));
                ColorStateList unselected = ColorStateList.valueOf(Color.parseColor("#FFFFFFFF"));
                if (v.getBackgroundTintList().equals(unselected)){
                    v.setBackgroundTintList(selected);
                } else{
                    v.setBackgroundTintList(unselected);
                }
            }
        };

        ArrayList<ImageButton> buttons = new ArrayList<ImageButton>();
        buttons.add((FloatingActionButton) findViewById(R.id.cameraReview));
        buttons.add((FloatingActionButton) findViewById(R.id.alldayReview));
        buttons.add((FloatingActionButton) findViewById(R.id.chargeReview));
        buttons.add((FloatingActionButton) findViewById(R.id.securityReview));
        buttons.add((FloatingActionButton) findViewById(R.id.verifiedReview));
        buttons.add((FloatingActionButton) findViewById(R.id.shelterReview));

        for (ImageButton b: buttons){
            b.setOnClickListener(onClickSetTint);
        }

    }



}
