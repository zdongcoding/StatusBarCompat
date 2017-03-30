package com.github.statusbarcompat.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setStatusBarColor(View view) {
        startActivity(new Intent(this,StatusBarColorActivity.class));
    }

    public void translucentStatusBar(View view) {
        startActivity(new Intent(this,TranslucentStatusBarActivity.class));

    }

    public void setStatusBarColorForCollapsingToolbar(View view) {
        startActivity(new Intent(this,CollapsingToolbarrActivity.class));

    }
}
