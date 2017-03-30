package com.github.statusbarcompat.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.statusbarcompat.StatusBarCompat;


public class TranslucentStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_status_bar);

    }

    public void setStatusBar(View view) {
        StatusBarCompat.translucentStatusBar(this);
    }

    public void clearStatusBar(View view) {
        StatusBarCompat.clearTranslucent(this);
    }
}
