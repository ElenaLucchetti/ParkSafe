package com.example.parksafe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;


public class WriteReview extends AppCompatActivity {

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
    }


}
