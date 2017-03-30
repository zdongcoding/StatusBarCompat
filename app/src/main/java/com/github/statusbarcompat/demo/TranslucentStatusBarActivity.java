package com.github.statusbarcompat.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.github.statusbarcompat.StatusBarCompat;


public class TranslucentStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_status_bar);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        Log.e("zoudong", "onWindowAttributesChanged====" + "params = [" + params + "]");
    }

    public void setStatusBar(View view) {
        StatusBarCompat.translucentStatusBar(this);
    }

    public void clearStatusBar(View view) {
        StatusBarCompat.clearTranslucent(this);
    }
}
