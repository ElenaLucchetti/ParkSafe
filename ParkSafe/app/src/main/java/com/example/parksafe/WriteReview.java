package com.example.parksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


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
        submitButton.setOnClickListener(onClickFinish);
    }


}
