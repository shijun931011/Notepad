package com.example.shijun.notepad.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shijun.notepad.R;

public class TransitionActivity extends AppCompatActivity {

    private Intent intent;
    Boolean isFirstIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data", MODE_PRIVATE);
        isFirstIn = sharedPreferences.getBoolean("isFirstin", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstIn){
                    intent = new Intent(TransitionActivity.this, GuideActivity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                }else{
                    intent = new Intent(TransitionActivity.this, MainActivity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();

                }
            }
        },2000);

    }
}
