package com.example.shijun.notepad.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.shijun.notepad.R;

public class AboutActivity extends AppCompatActivity {

    private ImageButton mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mBackBtn = (ImageButton) findViewById(R.id.about_back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
