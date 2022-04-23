package com.example.bros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Spash_Screen extends AppCompatActivity {
    private static int FLASH_SCREEN = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Spash_Screen.this,Log_in.class);
                startActivity(i);
                finish();
            }
        },FLASH_SCREEN);
    }
}