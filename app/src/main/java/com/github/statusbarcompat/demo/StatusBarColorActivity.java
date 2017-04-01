package com.github.statusbarcompat.demo;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.statusbarcompat.StatusBarCompat;

import java.util.Random;


public class StatusBarColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_color);
    }

    public void setStatusBar(View view) {
        Random random = new Random();
        StatusBarCompat.setStatusBarColor(this, Color.RED,random.nextInt(255));
        StatusBarCompat.setStatusBarLightMode(this,true);
    }

    public void clearStatusBar(View view) {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorPrimary));
    }
}
